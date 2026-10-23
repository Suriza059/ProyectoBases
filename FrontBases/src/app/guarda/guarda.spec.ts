import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Guarda } from './guarda';

describe('Guarda', () => {
  let component: Guarda;
  let fixture: ComponentFixture<Guarda>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Guarda],
    }).compileComponents();

    fixture = TestBed.createComponent(Guarda);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
