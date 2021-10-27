import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParentDashboardHomeComponent } from './parent-dashboard-home.component';

describe('ParentDashboardHomeComponent', () => {
  let component: ParentDashboardHomeComponent;
  let fixture: ComponentFixture<ParentDashboardHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ParentDashboardHomeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ParentDashboardHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
