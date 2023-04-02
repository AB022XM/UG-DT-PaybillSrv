import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAssociatedFees } from '../associated-fees.model';

@Component({
  selector: 'jhi-associated-fees-detail',
  templateUrl: './associated-fees-detail.component.html',
})
export class AssociatedFeesDetailComponent implements OnInit {
  associatedFees: IAssociatedFees | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ associatedFees }) => {
      this.associatedFees = associatedFees;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
