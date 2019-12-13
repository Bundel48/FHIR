import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MainVacPageComponent } from './main-vac-page.component';

describe('MainVacPageComponent', () => {
  let component: MainVacPageComponent;
  let fixture: ComponentFixture<MainVacPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MainVacPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MainVacPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
