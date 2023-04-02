import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ValidateCustomerByIdService } from '../service/validate-customer-by-id.service';

import { ValidateCustomerByIdComponent } from './validate-customer-by-id.component';

describe('ValidateCustomerById Management Component', () => {
  let comp: ValidateCustomerByIdComponent;
  let fixture: ComponentFixture<ValidateCustomerByIdComponent>;
  let service: ValidateCustomerByIdService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'validate-customer-by-id', component: ValidateCustomerByIdComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [ValidateCustomerByIdComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(ValidateCustomerByIdComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ValidateCustomerByIdComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ValidateCustomerByIdService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.validateCustomerByIds?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to validateCustomerByIdService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getValidateCustomerByIdIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getValidateCustomerByIdIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
