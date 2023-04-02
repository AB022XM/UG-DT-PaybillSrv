import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBillerCatergory } from '../biller-catergory.model';
import { BillerCatergoryService } from '../service/biller-catergory.service';

@Injectable({ providedIn: 'root' })
export class BillerCatergoryRoutingResolveService implements Resolve<IBillerCatergory | null> {
  constructor(protected service: BillerCatergoryService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBillerCatergory | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((billerCatergory: HttpResponse<IBillerCatergory>) => {
          if (billerCatergory.body) {
            return of(billerCatergory.body);
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
