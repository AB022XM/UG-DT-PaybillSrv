import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ContactInfoFormService } from './contact-info-form.service';
import { ContactInfoService } from '../service/contact-info.service';
import { IContactInfo } from '../contact-info.model';
import { IStudent } from 'app/entities/student/student.model';
import { StudentService } from 'app/entities/student/service/student.service';

import { ContactInfoUpdateComponent } from './contact-info-update.component';

describe('ContactInfo Management Update Component', () => {
  let comp: ContactInfoUpdateComponent;
  let fixture: ComponentFixture<ContactInfoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contactInfoFormService: ContactInfoFormService;
  let contactInfoService: ContactInfoService;
  let studentService: StudentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ContactInfoUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ContactInfoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContactInfoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contactInfoFormService = TestBed.inject(ContactInfoFormService);
    contactInfoService = TestBed.inject(ContactInfoService);
    studentService = TestBed.inject(StudentService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Student query and add missing value', () => {
      const contactInfo: IContactInfo = { id: 456 };
      const student: IStudent = { id: 43582 };
      contactInfo.student = student;

      const studentCollection: IStudent[] = [{ id: 76690 }];
      jest.spyOn(studentService, 'query').mockReturnValue(of(new HttpResponse({ body: studentCollection })));
      const additionalStudents = [student];
      const expectedCollection: IStudent[] = [...additionalStudents, ...studentCollection];
      jest.spyOn(studentService, 'addStudentToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contactInfo });
      comp.ngOnInit();

      expect(studentService.query).toHaveBeenCalled();
      expect(studentService.addStudentToCollectionIfMissing).toHaveBeenCalledWith(
        studentCollection,
        ...additionalStudents.map(expect.objectContaining)
      );
      expect(comp.studentsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const contactInfo: IContactInfo = { id: 456 };
      const student: IStudent = { id: 67520 };
      contactInfo.student = student;

      activatedRoute.data = of({ contactInfo });
      comp.ngOnInit();

      expect(comp.studentsSharedCollection).toContain(student);
      expect(comp.contactInfo).toEqual(contactInfo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContactInfo>>();
      const contactInfo = { id: 123 };
      jest.spyOn(contactInfoFormService, 'getContactInfo').mockReturnValue(contactInfo);
      jest.spyOn(contactInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contactInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contactInfo }));
      saveSubject.complete();

      // THEN
      expect(contactInfoFormService.getContactInfo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(contactInfoService.update).toHaveBeenCalledWith(expect.objectContaining(contactInfo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContactInfo>>();
      const contactInfo = { id: 123 };
      jest.spyOn(contactInfoFormService, 'getContactInfo').mockReturnValue({ id: null });
      jest.spyOn(contactInfoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contactInfo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contactInfo }));
      saveSubject.complete();

      // THEN
      expect(contactInfoFormService.getContactInfo).toHaveBeenCalled();
      expect(contactInfoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContactInfo>>();
      const contactInfo = { id: 123 };
      jest.spyOn(contactInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contactInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contactInfoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareStudent', () => {
      it('Should forward to studentService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(studentService, 'compareStudent');
        comp.compareStudent(entity, entity2);
        expect(studentService.compareStudent).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
