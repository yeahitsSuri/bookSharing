import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookRoutingModule } from './book-routing.module';
import { MainComponent } from './pages/main/main.component';
import { BookListComponent } from './pages/book-list/book-list.component';

@NgModule({
  declarations: [],
  imports: [
    BookListComponent,
    MainComponent,
    CommonModule,
    BookRoutingModule
  ]
})
export class BookModule { }
