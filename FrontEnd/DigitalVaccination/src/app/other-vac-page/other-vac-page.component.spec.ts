import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OtherVacPageComponent } from './other-vac-page.component';

describe('OtherVacPageComponent', () => {
  let component: OtherVacPageComponent;
  let fixture: ComponentFixture<OtherVacPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OtherVacPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OtherVacPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
