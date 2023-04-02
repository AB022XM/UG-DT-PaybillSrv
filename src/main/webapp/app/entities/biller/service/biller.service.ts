import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBiller, NewBiller } from '../biller.model';

export type PartialUpdateBiller = Partial<IBiller> & Pick<IBiller, 'id'>;

type RestOf<T extends IBiller | NewBiller> = Omit<T, 'dateOfBirth' | 'isDeleted'> & {
  dateOfBirth?: string | null;
  isDeleted?: string | null;
};

export type RestBiller = RestOf<IBiller>;

export type NewRestBiller = RestOf<NewBiller>;

export type PartialUpdateRestBiller = RestOf<PartialUpdateBiller>;

export type EntityResponseType = HttpResponse<IBiller>;
export type EntityArrayResponseType = HttpResponse<IBiller[]>;

@Injectable({ providedIn: 'root' })
export class BillerService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/billers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(biller: NewBiller): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(biller);
    return this.http
      .post<RestBiller>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(biller: IBiller): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(biller);
    return this.http
      .put<RestBiller>(`${this.resourceUrl}/${this.getBillerIdentifier(biller)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(biller: PartialUpdateBiller): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(biller);
    return this.http
      .patch<RestBiller>(`${this.resourceUrl}/${this.getBillerIdentifier(biller)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestBiller>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestBiller[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBillerIdentifier(biller: Pick<IBiller, 'id'>): number {
    return biller.id;
  }

  compareBiller(o1: Pick<IBiller, 'id'> | null, o2: Pick<IBiller, 'id'> | null): boolean {
    return o1 && o2 ? this.getBillerIdentifier(o1) === this.getBillerIdentifier(o2) : o1 === o2;
  }

  addBillerToCollectionIfMissing<Type extends Pick<IBiller, 'id'>>(
    billerCollection: Type[],
    ...billersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const billers: Type[] = billersToCheck.filter(isPresent);
    if (billers.length > 0) {
      const billerCollectionIdentifiers = billerCollection.map(billerItem => this.getBillerIdentifier(billerItem)!);
      const billersToAdd = billers.filter(billerItem => {
        const billerIdentifier = this.getBillerIdentifier(billerItem);
        if (billerCollectionIdentifiers.includes(billerIdentifier)) {
          return false;
        }
        billerCollectionIdentifiers.push(billerIdentifier);
        return true;
      });
      return [...billersToAdd, ...billerCollection];
    }
    return billerCollection;
  }

  protected convertDateFromClient<T extends IBiller | NewBiller | PartialUpdateBiller>(biller: T): RestOf<T> {
    return {
      ...biller,
      dateOfBirth: biller.dateOfBirth?.format(DATE_FORMAT) ?? null,
      isDeleted: biller.isDeleted?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restBiller: RestBiller): IBiller {
    return {
      ...restBiller,
      dateOfBirth: restBiller.dateOfBirth ? dayjs(restBiller.dateOfBirth) : undefined,
      isDeleted: restBiller.isDeleted ? dayjs(restBiller.isDeleted) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestBiller>): HttpResponse<IBiller> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestBiller[]>): HttpResponse<IBiller[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
