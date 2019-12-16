import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HebBVacPageComponent } from './heb-b-vac-page.component';

describe('HebBVacPageComponent', () => {
  let component: HebBVacPageComponent;
  let fixture: ComponentFixture<HebBVacPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HebBVacPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HebBVacPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
