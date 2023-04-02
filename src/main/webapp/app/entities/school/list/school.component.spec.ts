import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { SchoolService } from '../service/school.service';

import { SchoolComponent } from './school.component';

describe('School Management Component', () => {
  let comp: SchoolComponent;
  let fixture: ComponentFixture<SchoolComponent>;
  let service: SchoolService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'school', component: SchoolComponent }]), HttpClientTestingModule],
      declarations: [SchoolComponent],
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
      .overrideTemplate(SchoolComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SchoolComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SchoolService);

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
    expect(comp.schools?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to schoolService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getSchoolIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getSchoolIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
