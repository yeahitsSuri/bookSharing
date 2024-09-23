import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent } from './pages/main/main.component';
import { BookListComponent } from './pages/book-list/book-list.component';

const routes: Routes = [
  {
    path: '', // load the main component by default
    component: MainComponent,
    children: [
      {
        path: '', // the path is inherited from the parent which is "localhost:4200/books"
        component: BookListComponent
      }
    ]
  }
];
console.log('routes', routes);

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookRoutingModule { }
