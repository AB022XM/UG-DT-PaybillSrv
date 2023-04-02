import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPaybill } from '../paybill.model';
import { PaybillService } from '../service/paybill.service';

@Injectable({ providedIn: 'root' })
export class PaybillRoutingResolveService implements Resolve<IPaybill | null> {
  constructor(protected service: PaybillService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaybill | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((paybill: HttpResponse<IPaybill>) => {
          if (paybill.body) {
            return of(paybill.body);
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
