import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAssociatedFees } from '../associated-fees.model';
import { AssociatedFeesService } from '../service/associated-fees.service';

@Injectable({ providedIn: 'root' })
export class AssociatedFeesRoutingResolveService implements Resolve<IAssociatedFees | null> {
  constructor(protected service: AssociatedFeesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAssociatedFees | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((associatedFees: HttpResponse<IAssociatedFees>) => {
          if (associatedFees.body) {
            return of(associatedFees.body);
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
