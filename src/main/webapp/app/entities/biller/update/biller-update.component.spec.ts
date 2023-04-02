import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BillerFormService } from './biller-form.service';
import { BillerService } from '../service/biller.service';
import { IBiller } from '../biller.model';

import { BillerUpdateComponent } from './biller-update.component';

describe('Biller Management Update Component', () => {
  let comp: BillerUpdateComponent;
  let fixture: ComponentFixture<BillerUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let billerFormService: BillerFormService;
  let billerService: BillerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BillerUpdateComponent],
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
      .overrideTemplate(BillerUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BillerUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    billerFormService = TestBed.inject(BillerFormService);
    billerService = TestBed.inject(BillerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const biller: IBiller = { id: 456 };

      activatedRoute.data = of({ biller });
      comp.ngOnInit();

      expect(comp.biller).toEqual(biller);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBiller>>();
      const biller = { id: 123 };
      jest.spyOn(billerFormService, 'getBiller').mockReturnValue(biller);
      jest.spyOn(billerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ biller });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: biller }));
      saveSubject.complete();

      // THEN
      expect(billerFormService.getBiller).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(billerService.update).toHaveBeenCalledWith(expect.objectContaining(biller));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBiller>>();
      const biller = { id: 123 };
      jest.spyOn(billerFormService, 'getBiller').mockReturnValue({ id: null });
      jest.spyOn(billerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ biller: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: biller }));
      saveSubject.complete();

      // THEN
      expect(billerFormService.getBiller).toHaveBeenCalled();
      expect(billerService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBiller>>();
      const biller = { id: 123 };
      jest.spyOn(billerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ biller });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(billerService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
