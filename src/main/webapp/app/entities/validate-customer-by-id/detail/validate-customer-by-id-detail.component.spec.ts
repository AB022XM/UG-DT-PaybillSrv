import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ValidateCustomerByIdDetailComponent } from './validate-customer-by-id-detail.component';

describe('ValidateCustomerById Management Detail Component', () => {
  let comp: ValidateCustomerByIdDetailComponent;
  let fixture: ComponentFixture<ValidateCustomerByIdDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ValidateCustomerByIdDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ validateCustomerById: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ValidateCustomerByIdDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ValidateCustomerByIdDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load validateCustomerById on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.validateCustomerById).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
