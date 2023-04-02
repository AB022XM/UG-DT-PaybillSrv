import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BillerCatergoryFormService } from './biller-catergory-form.service';
import { BillerCatergoryService } from '../service/biller-catergory.service';
import { IBillerCatergory } from '../biller-catergory.model';

import { BillerCatergoryUpdateComponent } from './biller-catergory-update.component';

describe('BillerCatergory Management Update Component', () => {
  let comp: BillerCatergoryUpdateComponent;
  let fixture: ComponentFixture<BillerCatergoryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let billerCatergoryFormService: BillerCatergoryFormService;
  let billerCatergoryService: BillerCatergoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BillerCatergoryUpdateComponent],
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
      .overrideTemplate(BillerCatergoryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BillerCatergoryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    billerCatergoryFormService = TestBed.inject(BillerCatergoryFormService);
    billerCatergoryService = TestBed.inject(BillerCatergoryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const billerCatergory: IBillerCatergory = { id: 456 };

      activatedRoute.data = of({ billerCatergory });
      comp.ngOnInit();

      expect(comp.billerCatergory).toEqual(billerCatergory);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBillerCatergory>>();
      const billerCatergory = { id: 123 };
      jest.spyOn(billerCatergoryFormService, 'getBillerCatergory').mockReturnValue(billerCatergory);
      jest.spyOn(billerCatergoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ billerCatergory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: billerCatergory }));
      saveSubject.complete();

      // THEN
      expect(billerCatergoryFormService.getBillerCatergory).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(billerCatergoryService.update).toHaveBeenCalledWith(expect.objectContaining(billerCatergory));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBillerCatergory>>();
      const billerCatergory = { id: 123 };
      jest.spyOn(billerCatergoryFormService, 'getBillerCatergory').mockReturnValue({ id: null });
      jest.spyOn(billerCatergoryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ billerCatergory: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: billerCatergory }));
      saveSubject.complete();

      // THEN
      expect(billerCatergoryFormService.getBillerCatergory).toHaveBeenCalled();
      expect(billerCatergoryService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBillerCatergory>>();
      const billerCatergory = { id: 123 };
      jest.spyOn(billerCatergoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ billerCatergory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(billerCatergoryService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
