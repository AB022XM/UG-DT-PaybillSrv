import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { BillerCatergoryFormService, BillerCatergoryFormGroup } from './biller-catergory-form.service';
import { IBillerCatergory } from '../biller-catergory.model';
import { BillerCatergoryService } from '../service/biller-catergory.service';
import { RecordStatus } from 'app/entities/enumerations/record-status.model';

@Component({
  selector: 'jhi-biller-catergory-update',
  templateUrl: './biller-catergory-update.component.html',
})
export class BillerCatergoryUpdateComponent implements OnInit {
  isSaving = false;
  billerCatergory: IBillerCatergory | null = null;
  recordStatusValues = Object.keys(RecordStatus);

  editForm: BillerCatergoryFormGroup = this.billerCatergoryFormService.createBillerCatergoryFormGroup();

  constructor(
    protected billerCatergoryService: BillerCatergoryService,
    protected billerCatergoryFormService: BillerCatergoryFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ billerCatergory }) => {
      this.billerCatergory = billerCatergory;
      if (billerCatergory) {
        this.updateForm(billerCatergory);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const billerCatergory = this.billerCatergoryFormService.getBillerCatergory(this.editForm);
    if (billerCatergory.id !== null) {
      this.subscribeToSaveResponse(this.billerCatergoryService.update(billerCatergory));
    } else {
      this.subscribeToSaveResponse(this.billerCatergoryService.create(billerCatergory));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBillerCatergory>>): void {
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

  protected updateForm(billerCatergory: IBillerCatergory): void {
    this.billerCatergory = billerCatergory;
    this.billerCatergoryFormService.resetForm(this.editForm, billerCatergory);
  }
}
