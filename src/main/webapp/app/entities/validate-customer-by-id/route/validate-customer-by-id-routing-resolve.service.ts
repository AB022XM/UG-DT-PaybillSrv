import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IValidateCustomerById } from '../validate-customer-by-id.model';
import { ValidateCustomerByIdService } from '../service/validate-customer-by-id.service';

@Injectable({ providedIn: 'root' })
export class ValidateCustomerByIdRoutingResolveService implements Resolve<IValidateCustomerById | null> {
  constructor(protected service: ValidateCustomerByIdService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IValidateCustomerById | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((validateCustomerById: HttpResponse<IValidateCustomerById>) => {
          if (validateCustomerById.body) {
            return of(validateCustomerById.body);
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
