import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IStudent, NewStudent } from '../student.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IStudent for edit and NewStudentFormGroupInput for create.
 */
type StudentFormGroupInput = IStudent | PartialWithRequiredKeyOf<NewStudent>;

type StudentFormDefaults = Pick<NewStudent, 'id'>;

type StudentFormGroupContent = {
  id: FormControl<IStudent['id'] | NewStudent['id']>;
  recordUniqueIdentifier: FormControl<IStudent['recordUniqueIdentifier']>;
  studentId: FormControl<IStudent['studentId']>;
  studentCode: FormControl<IStudent['studentCode']>;
  studentClassId: FormControl<IStudent['studentClassId']>;
  contactId: FormControl<IStudent['contactId']>;
  addressId: FormControl<IStudent['addressId']>;
  firstName: FormControl<IStudent['firstName']>;
  middleName: FormControl<IStudent['middleName']>;
  lastName: FormControl<IStudent['lastName']>;
  paymentCode: FormControl<IStudent['paymentCode']>;
  dateOfBirth: FormControl<IStudent['dateOfBirth']>;
  outStandingAmount: FormControl<IStudent['outStandingAmount']>;
  status: FormControl<IStudent['status']>;
  billerContact: FormControl<IStudent['billerContact']>;
  billerAddress: FormControl<IStudent['billerAddress']>;
  freeField1: FormControl<IStudent['freeField1']>;
  freeField2: FormControl<IStudent['freeField2']>;
  freeField3: FormControl<IStudent['freeField3']>;
  isDeleted: FormControl<IStudent['isDeleted']>;
};

export type StudentFormGroup = FormGroup<StudentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class StudentFormService {
  createStudentFormGroup(student: StudentFormGroupInput = { id: null }): StudentFormGroup {
    const studentRawValue = {
      ...this.getFormDefaults(),
      ...student,
    };
    return new FormGroup<StudentFormGroupContent>({
      id: new FormControl(
        { value: studentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      recordUniqueIdentifier: new FormControl(studentRawValue.recordUniqueIdentifier, {
        validators: [Validators.required],
      }),
      studentId: new FormControl(studentRawValue.studentId, {
        validators: [Validators.required],
      }),
      studentCode: new FormControl(studentRawValue.studentCode, {
        validators: [Validators.required],
      }),
      studentClassId: new FormControl(studentRawValue.studentClassId, {
        validators: [Validators.required],
      }),
      contactId: new FormControl(studentRawValue.contactId, {
        validators: [Validators.required],
      }),
      addressId: new FormControl(studentRawValue.addressId, {
        validators: [Validators.required],
      }),
      firstName: new FormControl(studentRawValue.firstName, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(30)],
      }),
      middleName: new FormControl(studentRawValue.middleName, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(30)],
      }),
      lastName: new FormControl(studentRawValue.lastName, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(30)],
      }),
      paymentCode: new FormControl(studentRawValue.paymentCode, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(20)],
      }),
      dateOfBirth: new FormControl(studentRawValue.dateOfBirth, {
        validators: [Validators.required],
      }),
      outStandingAmount: new FormControl(studentRawValue.outStandingAmount, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(8)],
      }),
      status: new FormControl(studentRawValue.status, {
        validators: [Validators.required],
      }),
      billerContact: new FormControl(studentRawValue.billerContact),
      billerAddress: new FormControl(studentRawValue.billerAddress),
      freeField1: new FormControl(studentRawValue.freeField1),
      freeField2: new FormControl(studentRawValue.freeField2),
      freeField3: new FormControl(studentRawValue.freeField3),
      isDeleted: new FormControl(studentRawValue.isDeleted),
    });
  }

  getStudent(form: StudentFormGroup): IStudent | NewStudent {
    return form.getRawValue() as IStudent | NewStudent;
  }

  resetForm(form: StudentFormGroup, student: StudentFormGroupInput): void {
    const studentRawValue = { ...this.getFormDefaults(), ...student };
    form.reset(
      {
        ...studentRawValue,
        id: { value: studentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): StudentFormDefaults {
    return {
      id: null,
    };
  }
}
