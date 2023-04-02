import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAssociatedFees, NewAssociatedFees } from '../associated-fees.model';

export type PartialUpdateAssociatedFees = Partial<IAssociatedFees> & Pick<IAssociatedFees, 'id'>;

type RestOf<T extends IAssociatedFees | NewAssociatedFees> = Omit<T, 'createdAt' | 'updatedAt' | 'isDeleted'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
  isDeleted?: string | null;
};

export type RestAssociatedFees = RestOf<IAssociatedFees>;

export type NewRestAssociatedFees = RestOf<NewAssociatedFees>;

export type PartialUpdateRestAssociatedFees = RestOf<PartialUpdateAssociatedFees>;

export type EntityResponseType = HttpResponse<IAssociatedFees>;
export type EntityArrayResponseType = HttpResponse<IAssociatedFees[]>;

@Injectable({ providedIn: 'root' })
export class AssociatedFeesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/associated-fees');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(associatedFees: NewAssociatedFees): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(associatedFees);
    return this.http
      .post<RestAssociatedFees>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(associatedFees: IAssociatedFees): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(associatedFees);
    return this.http
      .put<RestAssociatedFees>(`${this.resourceUrl}/${this.getAssociatedFeesIdentifier(associatedFees)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(associatedFees: PartialUpdateAssociatedFees): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(associatedFees);
    return this.http
      .patch<RestAssociatedFees>(`${this.resourceUrl}/${this.getAssociatedFeesIdentifier(associatedFees)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAssociatedFees>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAssociatedFees[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAssociatedFeesIdentifier(associatedFees: Pick<IAssociatedFees, 'id'>): number {
    return associatedFees.id;
  }

  compareAssociatedFees(o1: Pick<IAssociatedFees, 'id'> | null, o2: Pick<IAssociatedFees, 'id'> | null): boolean {
    return o1 && o2 ? this.getAssociatedFeesIdentifier(o1) === this.getAssociatedFeesIdentifier(o2) : o1 === o2;
  }

  addAssociatedFeesToCollectionIfMissing<Type extends Pick<IAssociatedFees, 'id'>>(
    associatedFeesCollection: Type[],
    ...associatedFeesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const associatedFees: Type[] = associatedFeesToCheck.filter(isPresent);
    if (associatedFees.length > 0) {
      const associatedFeesCollectionIdentifiers = associatedFeesCollection.map(
        associatedFeesItem => this.getAssociatedFeesIdentifier(associatedFeesItem)!
      );
      const associatedFeesToAdd = associatedFees.filter(associatedFeesItem => {
        const associatedFeesIdentifier = this.getAssociatedFeesIdentifier(associatedFeesItem);
        if (associatedFeesCollectionIdentifiers.includes(associatedFeesIdentifier)) {
          return false;
        }
        associatedFeesCollectionIdentifiers.push(associatedFeesIdentifier);
        return true;
      });
      return [...associatedFeesToAdd, ...associatedFeesCollection];
    }
    return associatedFeesCollection;
  }

  protected convertDateFromClient<T extends IAssociatedFees | NewAssociatedFees | PartialUpdateAssociatedFees>(
    associatedFees: T
  ): RestOf<T> {
    return {
      ...associatedFees,
      createdAt: associatedFees.createdAt?.format(DATE_FORMAT) ?? null,
      updatedAt: associatedFees.updatedAt?.format(DATE_FORMAT) ?? null,
      isDeleted: associatedFees.isDeleted?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restAssociatedFees: RestAssociatedFees): IAssociatedFees {
    return {
      ...restAssociatedFees,
      createdAt: restAssociatedFees.createdAt ? dayjs(restAssociatedFees.createdAt) : undefined,
      updatedAt: restAssociatedFees.updatedAt ? dayjs(restAssociatedFees.updatedAt) : undefined,
      isDeleted: restAssociatedFees.isDeleted ? dayjs(restAssociatedFees.isDeleted) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAssociatedFees>): HttpResponse<IAssociatedFees> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAssociatedFees[]>): HttpResponse<IAssociatedFees[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
