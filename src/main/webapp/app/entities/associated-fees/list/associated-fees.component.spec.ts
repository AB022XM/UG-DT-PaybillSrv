import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { AssociatedFeesService } from '../service/associated-fees.service';

import { AssociatedFeesComponent } from './associated-fees.component';

describe('AssociatedFees Management Component', () => {
  let comp: AssociatedFeesComponent;
  let fixture: ComponentFixture<AssociatedFeesComponent>;
  let service: AssociatedFeesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'associated-fees', component: AssociatedFeesComponent }]), HttpClientTestingModule],
      declarations: [AssociatedFeesComponent],
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
      .overrideTemplate(AssociatedFeesComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AssociatedFeesComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AssociatedFeesService);

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
    expect(comp.associatedFees?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to associatedFeesService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getAssociatedFeesIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getAssociatedFeesIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
