import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PaybillFormService } from './paybill-form.service';
import { PaybillService } from '../service/paybill.service';
import { IPaybill } from '../paybill.model';
import { IBiller } from 'app/entities/biller/biller.model';
import { BillerService } from 'app/entities/biller/service/biller.service';

import { PaybillUpdateComponent } from './paybill-update.component';

describe('Paybill Management Update Component', () => {
  let comp: PaybillUpdateComponent;
  let fixture: ComponentFixture<PaybillUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paybillFormService: PaybillFormService;
  let paybillService: PaybillService;
  let billerService: BillerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PaybillUpdateComponent],
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
      .overrideTemplate(PaybillUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaybillUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    paybillFormService = TestBed.inject(PaybillFormService);
    paybillService = TestBed.inject(PaybillService);
    billerService = TestBed.inject(BillerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Biller query and add missing value', () => {
      const paybill: IPaybill = { id: 456 };
      const biller: IBiller = { id: 74129 };
      paybill.biller = biller;

      const billerCollection: IBiller[] = [{ id: 50627 }];
      jest.spyOn(billerService, 'query').mockReturnValue(of(new HttpResponse({ body: billerCollection })));
      const additionalBillers = [biller];
      const expectedCollection: IBiller[] = [...additionalBillers, ...billerCollection];
      jest.spyOn(billerService, 'addBillerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ paybill });
      comp.ngOnInit();

      expect(billerService.query).toHaveBeenCalled();
      expect(billerService.addBillerToCollectionIfMissing).toHaveBeenCalledWith(
        billerCollection,
        ...additionalBillers.map(expect.objectContaining)
      );
      expect(comp.billersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const paybill: IPaybill = { id: 456 };
      const biller: IBiller = { id: 21644 };
      paybill.biller = biller;

      activatedRoute.data = of({ paybill });
      comp.ngOnInit();

      expect(comp.billersSharedCollection).toContain(biller);
      expect(comp.paybill).toEqual(paybill);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaybill>>();
      const paybill = { id: 123 };
      jest.spyOn(paybillFormService, 'getPaybill').mockReturnValue(paybill);
      jest.spyOn(paybillService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paybill });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paybill }));
      saveSubject.complete();

      // THEN
      expect(paybillFormService.getPaybill).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(paybillService.update).toHaveBeenCalledWith(expect.objectContaining(paybill));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaybill>>();
      const paybill = { id: 123 };
      jest.spyOn(paybillFormService, 'getPaybill').mockReturnValue({ id: null });
      jest.spyOn(paybillService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paybill: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paybill }));
      saveSubject.complete();

      // THEN
      expect(paybillFormService.getPaybill).toHaveBeenCalled();
      expect(paybillService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaybill>>();
      const paybill = { id: 123 };
      jest.spyOn(paybillService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paybill });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(paybillService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareBiller', () => {
      it('Should forward to billerService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(billerService, 'compareBiller');
        comp.compareBiller(entity, entity2);
        expect(billerService.compareBiller).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
