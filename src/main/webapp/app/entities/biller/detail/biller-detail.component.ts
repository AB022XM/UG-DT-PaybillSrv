import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBiller } from '../biller.model';

@Component({
  selector: 'jhi-biller-detail',
  templateUrl: './biller-detail.component.html',
})
export class BillerDetailComponent implements OnInit {
  biller: IBiller | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ biller }) => {
      this.biller = biller;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
