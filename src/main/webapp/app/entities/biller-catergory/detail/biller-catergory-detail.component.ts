import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBillerCatergory } from '../biller-catergory.model';

@Component({
  selector: 'jhi-biller-catergory-detail',
  templateUrl: './biller-catergory-detail.component.html',
})
export class BillerCatergoryDetailComponent implements OnInit {
  billerCatergory: IBillerCatergory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ billerCatergory }) => {
      this.billerCatergory = billerCatergory;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
