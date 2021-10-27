import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DependentDashboardComponent } from './dependent-dashboard.component';

describe('DependentDashboardComponent', () => {
  let component: DependentDashboardComponent;
  let fixture: ComponentFixture<DependentDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DependentDashboardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DependentDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
