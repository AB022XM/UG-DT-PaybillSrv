import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISchool, NewSchool } from '../school.model';

export type PartialUpdateSchool = Partial<ISchool> & Pick<ISchool, 'id'>;

type RestOf<T extends ISchool | NewSchool> = Omit<T, 'registrationDate' | 'approvedDate' | 'isDeleted'> & {
  registrationDate?: string | null;
  approvedDate?: string | null;
  isDeleted?: string | null;
};

export type RestSchool = RestOf<ISchool>;

export type NewRestSchool = RestOf<NewSchool>;

export type PartialUpdateRestSchool = RestOf<PartialUpdateSchool>;

export type EntityResponseType = HttpResponse<ISchool>;
export type EntityArrayResponseType = HttpResponse<ISchool[]>;

@Injectable({ providedIn: 'root' })
export class SchoolService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/schools');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(school: NewSchool): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(school);
    return this.http
      .post<RestSchool>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(school: ISchool): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(school);
    return this.http
      .put<RestSchool>(`${this.resourceUrl}/${this.getSchoolIdentifier(school)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(school: PartialUpdateSchool): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(school);
    return this.http
      .patch<RestSchool>(`${this.resourceUrl}/${this.getSchoolIdentifier(school)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSchool>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSchool[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSchoolIdentifier(school: Pick<ISchool, 'id'>): number {
    return school.id;
  }

  compareSchool(o1: Pick<ISchool, 'id'> | null, o2: Pick<ISchool, 'id'> | null): boolean {
    return o1 && o2 ? this.getSchoolIdentifier(o1) === this.getSchoolIdentifier(o2) : o1 === o2;
  }

  addSchoolToCollectionIfMissing<Type extends Pick<ISchool, 'id'>>(
    schoolCollection: Type[],
    ...schoolsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const schools: Type[] = schoolsToCheck.filter(isPresent);
    if (schools.length > 0) {
      const schoolCollectionIdentifiers = schoolCollection.map(schoolItem => this.getSchoolIdentifier(schoolItem)!);
      const schoolsToAdd = schools.filter(schoolItem => {
        const schoolIdentifier = this.getSchoolIdentifier(schoolItem);
        if (schoolCollectionIdentifiers.includes(schoolIdentifier)) {
          return false;
        }
        schoolCollectionIdentifiers.push(schoolIdentifier);
        return true;
      });
      return [...schoolsToAdd, ...schoolCollection];
    }
    return schoolCollection;
  }

  protected convertDateFromClient<T extends ISchool | NewSchool | PartialUpdateSchool>(school: T): RestOf<T> {
    return {
      ...school,
      registrationDate: school.registrationDate?.toJSON() ?? null,
      approvedDate: school.approvedDate?.toJSON() ?? null,
      isDeleted: school.isDeleted?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restSchool: RestSchool): ISchool {
    return {
      ...restSchool,
      registrationDate: restSchool.registrationDate ? dayjs(restSchool.registrationDate) : undefined,
      approvedDate: restSchool.approvedDate ? dayjs(restSchool.approvedDate) : undefined,
      isDeleted: restSchool.isDeleted ? dayjs(restSchool.isDeleted) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSchool>): HttpResponse<ISchool> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSchool[]>): HttpResponse<ISchool[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
