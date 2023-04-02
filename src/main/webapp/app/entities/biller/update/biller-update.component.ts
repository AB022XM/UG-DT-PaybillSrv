import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { BillerFormService, BillerFormGroup } from './biller-form.service';
import { IBiller } from '../biller.model';
import { BillerService } from '../service/biller.service';
import { RecordStatus } from 'app/entities/enumerations/record-status.model';

@Component({
  selector: 'jhi-biller-update',
  templateUrl: './biller-update.component.html',
})
export class BillerUpdateComponent implements OnInit {
  isSaving = false;
  biller: IBiller | null = null;
  recordStatusValues = Object.keys(RecordStatus);

  editForm: BillerFormGroup = this.billerFormService.createBillerFormGroup();

  constructor(
    protected billerService: BillerService,
    protected billerFormService: BillerFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ biller }) => {
      this.biller = biller;
      if (biller) {
        this.updateForm(biller);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const biller = this.billerFormService.getBiller(this.editForm);
    if (biller.id !== null) {
      this.subscribeToSaveResponse(this.billerService.update(biller));
    } else {
      this.subscribeToSaveResponse(this.billerService.create(biller));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBiller>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(biller: IBiller): void {
    this.biller = biller;
    this.billerFormService.resetForm(this.editForm, biller);
  }
}
