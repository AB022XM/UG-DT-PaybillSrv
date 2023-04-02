import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBillerCatergory, NewBillerCatergory } from '../biller-catergory.model';

export type PartialUpdateBillerCatergory = Partial<IBillerCatergory> & Pick<IBillerCatergory, 'id'>;

export type EntityResponseType = HttpResponse<IBillerCatergory>;
export type EntityArrayResponseType = HttpResponse<IBillerCatergory[]>;

@Injectable({ providedIn: 'root' })
export class BillerCatergoryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/biller-catergories');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(billerCatergory: NewBillerCatergory): Observable<EntityResponseType> {
    return this.http.post<IBillerCatergory>(this.resourceUrl, billerCatergory, { observe: 'response' });
  }

  update(billerCatergory: IBillerCatergory): Observable<EntityResponseType> {
    return this.http.put<IBillerCatergory>(`${this.resourceUrl}/${this.getBillerCatergoryIdentifier(billerCatergory)}`, billerCatergory, {
      observe: 'response',
    });
  }

  partialUpdate(billerCatergory: PartialUpdateBillerCatergory): Observable<EntityResponseType> {
    return this.http.patch<IBillerCatergory>(`${this.resourceUrl}/${this.getBillerCatergoryIdentifier(billerCatergory)}`, billerCatergory, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBillerCatergory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBillerCatergory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBillerCatergoryIdentifier(billerCatergory: Pick<IBillerCatergory, 'id'>): number {
    return billerCatergory.id;
  }

  compareBillerCatergory(o1: Pick<IBillerCatergory, 'id'> | null, o2: Pick<IBillerCatergory, 'id'> | null): boolean {
    return o1 && o2 ? this.getBillerCatergoryIdentifier(o1) === this.getBillerCatergoryIdentifier(o2) : o1 === o2;
  }

  addBillerCatergoryToCollectionIfMissing<Type extends Pick<IBillerCatergory, 'id'>>(
    billerCatergoryCollection: Type[],
    ...billerCatergoriesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const billerCatergories: Type[] = billerCatergoriesToCheck.filter(isPresent);
    if (billerCatergories.length > 0) {
      const billerCatergoryCollectionIdentifiers = billerCatergoryCollection.map(
        billerCatergoryItem => this.getBillerCatergoryIdentifier(billerCatergoryItem)!
      );
      const billerCatergoriesToAdd = billerCatergories.filter(billerCatergoryItem => {
        const billerCatergoryIdentifier = this.getBillerCatergoryIdentifier(billerCatergoryItem);
        if (billerCatergoryCollectionIdentifiers.includes(billerCatergoryIdentifier)) {
          return false;
        }
        billerCatergoryCollectionIdentifiers.push(billerCatergoryIdentifier);
        return true;
      });
      return [...billerCatergoriesToAdd, ...billerCatergoryCollection];
    }
    return billerCatergoryCollection;
  }
}
