import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IValidateCustomerById, NewValidateCustomerById } from '../validate-customer-by-id.model';

export type PartialUpdateValidateCustomerById = Partial<IValidateCustomerById> & Pick<IValidateCustomerById, 'id'>;

export type EntityResponseType = HttpResponse<IValidateCustomerById>;
export type EntityArrayResponseType = HttpResponse<IValidateCustomerById[]>;

@Injectable({ providedIn: 'root' })
export class ValidateCustomerByIdService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/validate-customer-by-ids');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(validateCustomerById: NewValidateCustomerById): Observable<EntityResponseType> {
    return this.http.post<IValidateCustomerById>(this.resourceUrl, validateCustomerById, { observe: 'response' });
  }

  update(validateCustomerById: IValidateCustomerById): Observable<EntityResponseType> {
    return this.http.put<IValidateCustomerById>(
      `${this.resourceUrl}/${this.getValidateCustomerByIdIdentifier(validateCustomerById)}`,
      validateCustomerById,
      { observe: 'response' }
    );
  }

  partialUpdate(validateCustomerById: PartialUpdateValidateCustomerById): Observable<EntityResponseType> {
    return this.http.patch<IValidateCustomerById>(
      `${this.resourceUrl}/${this.getValidateCustomerByIdIdentifier(validateCustomerById)}`,
      validateCustomerById,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IValidateCustomerById>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IValidateCustomerById[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getValidateCustomerByIdIdentifier(validateCustomerById: Pick<IValidateCustomerById, 'id'>): number {
    return validateCustomerById.id;
  }

  compareValidateCustomerById(o1: Pick<IValidateCustomerById, 'id'> | null, o2: Pick<IValidateCustomerById, 'id'> | null): boolean {
    return o1 && o2 ? this.getValidateCustomerByIdIdentifier(o1) === this.getValidateCustomerByIdIdentifier(o2) : o1 === o2;
  }

  addValidateCustomerByIdToCollectionIfMissing<Type extends Pick<IValidateCustomerById, 'id'>>(
    validateCustomerByIdCollection: Type[],
    ...validateCustomerByIdsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const validateCustomerByIds: Type[] = validateCustomerByIdsToCheck.filter(isPresent);
    if (validateCustomerByIds.length > 0) {
      const validateCustomerByIdCollectionIdentifiers = validateCustomerByIdCollection.map(
        validateCustomerByIdItem => this.getValidateCustomerByIdIdentifier(validateCustomerByIdItem)!
      );
      const validateCustomerByIdsToAdd = validateCustomerByIds.filter(validateCustomerByIdItem => {
        const validateCustomerByIdIdentifier = this.getValidateCustomerByIdIdentifier(validateCustomerByIdItem);
        if (validateCustomerByIdCollectionIdentifiers.includes(validateCustomerByIdIdentifier)) {
          return false;
        }
        validateCustomerByIdCollectionIdentifiers.push(validateCustomerByIdIdentifier);
        return true;
      });
      return [...validateCustomerByIdsToAdd, ...validateCustomerByIdCollection];
    }
    return validateCustomerByIdCollection;
  }
}
