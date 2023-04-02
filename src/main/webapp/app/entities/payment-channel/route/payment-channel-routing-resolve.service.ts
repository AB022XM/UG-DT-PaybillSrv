import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPaymentChannel } from '../payment-channel.model';
import { PaymentChannelService } from '../service/payment-channel.service';

@Injectable({ providedIn: 'root' })
export class PaymentChannelRoutingResolveService implements Resolve<IPaymentChannel | null> {
  constructor(protected service: PaymentChannelService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaymentChannel | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((paymentChannel: HttpResponse<IPaymentChannel>) => {
          if (paymentChannel.body) {
            return of(paymentChannel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
