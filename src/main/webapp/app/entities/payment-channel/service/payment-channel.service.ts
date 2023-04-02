import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPaymentChannel, NewPaymentChannel } from '../payment-channel.model';

export type PartialUpdatePaymentChannel = Partial<IPaymentChannel> & Pick<IPaymentChannel, 'id'>;

export type EntityResponseType = HttpResponse<IPaymentChannel>;
export type EntityArrayResponseType = HttpResponse<IPaymentChannel[]>;

@Injectable({ providedIn: 'root' })
export class PaymentChannelService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/payment-channels');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(paymentChannel: NewPaymentChannel): Observable<EntityResponseType> {
    return this.http.post<IPaymentChannel>(this.resourceUrl, paymentChannel, { observe: 'response' });
  }

  update(paymentChannel: IPaymentChannel): Observable<EntityResponseType> {
    return this.http.put<IPaymentChannel>(`${this.resourceUrl}/${this.getPaymentChannelIdentifier(paymentChannel)}`, paymentChannel, {
      observe: 'response',
    });
  }

  partialUpdate(paymentChannel: PartialUpdatePaymentChannel): Observable<EntityResponseType> {
    return this.http.patch<IPaymentChannel>(`${this.resourceUrl}/${this.getPaymentChannelIdentifier(paymentChannel)}`, paymentChannel, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaymentChannel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaymentChannel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPaymentChannelIdentifier(paymentChannel: Pick<IPaymentChannel, 'id'>): number {
    return paymentChannel.id;
  }

  comparePaymentChannel(o1: Pick<IPaymentChannel, 'id'> | null, o2: Pick<IPaymentChannel, 'id'> | null): boolean {
    return o1 && o2 ? this.getPaymentChannelIdentifier(o1) === this.getPaymentChannelIdentifier(o2) : o1 === o2;
  }

  addPaymentChannelToCollectionIfMissing<Type extends Pick<IPaymentChannel, 'id'>>(
    paymentChannelCollection: Type[],
    ...paymentChannelsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const paymentChannels: Type[] = paymentChannelsToCheck.filter(isPresent);
    if (paymentChannels.length > 0) {
      const paymentChannelCollectionIdentifiers = paymentChannelCollection.map(
        paymentChannelItem => this.getPaymentChannelIdentifier(paymentChannelItem)!
      );
      const paymentChannelsToAdd = paymentChannels.filter(paymentChannelItem => {
        const paymentChannelIdentifier = this.getPaymentChannelIdentifier(paymentChannelItem);
        if (paymentChannelCollectionIdentifiers.includes(paymentChannelIdentifier)) {
          return false;
        }
        paymentChannelCollectionIdentifiers.push(paymentChannelIdentifier);
        return true;
      });
      return [...paymentChannelsToAdd, ...paymentChannelCollection];
    }
    return paymentChannelCollection;
  }
}
