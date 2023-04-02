import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ContactInfoFormService, ContactInfoFormGroup } from './contact-info-form.service';
import { IContactInfo } from '../contact-info.model';
import { ContactInfoService } from '../service/contact-info.service';
import { IStudent } from 'app/entities/student/student.model';
import { StudentService } from 'app/entities/student/service/student.service';
import { RecordStatus } from 'app/entities/enumerations/record-status.model';

@Component({
  selector: 'jhi-contact-info-update',
  templateUrl: './contact-info-update.component.html',
})
export class ContactInfoUpdateComponent implements OnInit {
  isSaving = false;
  contactInfo: IContactInfo | null = null;
  recordStatusValues = Object.keys(RecordStatus);

  studentsSharedCollection: IStudent[] = [];

  editForm: ContactInfoFormGroup = this.contactInfoFormService.createContactInfoFormGroup();

  constructor(
    protected contactInfoService: ContactInfoService,
    protected contactInfoFormService: ContactInfoFormService,
    protected studentService: StudentService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareStudent = (o1: IStudent | null, o2: IStudent | null): boolean => this.studentService.compareStudent(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactInfo }) => {
      this.contactInfo = contactInfo;
      if (contactInfo) {
        this.updateForm(contactInfo);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contactInfo = this.contactInfoFormService.getContactInfo(this.editForm);
    if (contactInfo.id !== null) {
      this.subscribeToSaveResponse(this.contactInfoService.update(contactInfo));
    } else {
      this.subscribeToSaveResponse(this.contactInfoService.create(contactInfo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContactInfo>>): void {
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

  protected updateForm(contactInfo: IContactInfo): void {
    this.contactInfo = contactInfo;
    this.contactInfoFormService.resetForm(this.editForm, contactInfo);

    this.studentsSharedCollection = this.studentService.addStudentToCollectionIfMissing<IStudent>(
      this.studentsSharedCollection,
      contactInfo.student
    );
  }

  protected loadRelationshipsOptions(): void {
    this.studentService
      .query()
      .pipe(map((res: HttpResponse<IStudent[]>) => res.body ?? []))
      .pipe(
        map((students: IStudent[]) => this.studentService.addStudentToCollectionIfMissing<IStudent>(students, this.contactInfo?.student))
      )
      .subscribe((students: IStudent[]) => (this.studentsSharedCollection = students));
  }
}
