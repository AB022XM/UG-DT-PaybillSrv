import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBiller } from '../biller.model';
import { BillerService } from '../service/biller.service';

@Injectable({ providedIn: 'root' })
export class BillerRoutingResolveService implements Resolve<IBiller | null> {
  constructor(protected service: BillerService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBiller | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((biller: HttpResponse<IBiller>) => {
          if (biller.body) {
            return of(biller.body);
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
