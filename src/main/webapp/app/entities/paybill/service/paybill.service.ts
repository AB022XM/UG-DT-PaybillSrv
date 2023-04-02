import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPaybill, NewPaybill } from '../paybill.model';

export type PartialUpdatePaybill = Partial<IPaybill> & Pick<IPaybill, 'id'>;

type RestOf<T extends IPaybill | NewPaybill> = Omit<T, 'processTimestamp' | 'feeDueFromDate' | 'feeDueToDate'> & {
  processTimestamp?: string | null;
  feeDueFromDate?: string | null;
  feeDueToDate?: string | null;
};

export type RestPaybill = RestOf<IPaybill>;

export type NewRestPaybill = RestOf<NewPaybill>;

export type PartialUpdateRestPaybill = RestOf<PartialUpdatePaybill>;

export type EntityResponseType = HttpResponse<IPaybill>;
export type EntityArrayResponseType = HttpResponse<IPaybill[]>;

@Injectable({ providedIn: 'root' })
export class PaybillService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/paybills');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(paybill: NewPaybill): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paybill);
    return this.http
      .post<RestPaybill>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(paybill: IPaybill): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paybill);
    return this.http
      .put<RestPaybill>(`${this.resourceUrl}/${this.getPaybillIdentifier(paybill)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(paybill: PartialUpdatePaybill): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paybill);
    return this.http
      .patch<RestPaybill>(`${this.resourceUrl}/${this.getPaybillIdentifier(paybill)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPaybill>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPaybill[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPaybillIdentifier(paybill: Pick<IPaybill, 'id'>): number {
    return paybill.id;
  }

  comparePaybill(o1: Pick<IPaybill, 'id'> | null, o2: Pick<IPaybill, 'id'> | null): boolean {
    return o1 && o2 ? this.getPaybillIdentifier(o1) === this.getPaybillIdentifier(o2) : o1 === o2;
  }

  addPaybillToCollectionIfMissing<Type extends Pick<IPaybill, 'id'>>(
    paybillCollection: Type[],
    ...paybillsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const paybills: Type[] = paybillsToCheck.filter(isPresent);
    if (paybills.length > 0) {
      const paybillCollectionIdentifiers = paybillCollection.map(paybillItem => this.getPaybillIdentifier(paybillItem)!);
      const paybillsToAdd = paybills.filter(paybillItem => {
        const paybillIdentifier = this.getPaybillIdentifier(paybillItem);
        if (paybillCollectionIdentifiers.includes(paybillIdentifier)) {
          return false;
        }
        paybillCollectionIdentifiers.push(paybillIdentifier);
        return true;
      });
      return [...paybillsToAdd, ...paybillCollection];
    }
    return paybillCollection;
  }

  protected convertDateFromClient<T extends IPaybill | NewPaybill | PartialUpdatePaybill>(paybill: T): RestOf<T> {
    return {
      ...paybill,
      processTimestamp: paybill.processTimestamp?.format(DATE_FORMAT) ?? null,
      feeDueFromDate: paybill.feeDueFromDate?.format(DATE_FORMAT) ?? null,
      feeDueToDate: paybill.feeDueToDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restPaybill: RestPaybill): IPaybill {
    return {
      ...restPaybill,
      processTimestamp: restPaybill.processTimestamp ? dayjs(restPaybill.processTimestamp) : undefined,
      feeDueFromDate: restPaybill.feeDueFromDate ? dayjs(restPaybill.feeDueFromDate) : undefined,
      feeDueToDate: restPaybill.feeDueToDate ? dayjs(restPaybill.feeDueToDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPaybill>): HttpResponse<IPaybill> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPaybill[]>): HttpResponse<IPaybill[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
