import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SerumVacPageComponent } from './serum-vac-page.component';

describe('SerumVacPageComponent', () => {
  let component: SerumVacPageComponent;
  let fixture: ComponentFixture<SerumVacPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SerumVacPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SerumVacPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
