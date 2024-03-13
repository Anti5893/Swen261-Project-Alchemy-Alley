import { NgModule, inject } from '@angular/core';
import { Routes, RouterModule, CanActivateFn, ActivatedRouteSnapshot,
         RouterStateSnapshot, createUrlTreeFromSnapshot, UrlTree } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { CatalogComponent } from './catalog/catalog.component';
import { CartComponent } from './cart/cart.component';
import { AdminComponent } from './admin/admin.component';
import { AdminProductDetailComponent } from './admin-product-detail/admin-product-detail.component';
import { CredentialsService } from './credentials.service';

const authGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot, 
                                    credentialsService = inject(CredentialsService)) => {
  const catalogPage: UrlTree = createUrlTreeFromSnapshot(route, ['/catalog']);
  const adminPage: UrlTree = createUrlTreeFromSnapshot(route, ['/admin']);

  if(route.routeConfig?.path === 'login' && credentialsService.isLoggedIn()) {
    return !credentialsService.isAdmin() ? catalogPage : adminPage;
  } else if(route.data?.['admin'] && !credentialsService.isAdmin()) {
    return catalogPage;
  } else if(!route.data?.['admin'] && credentialsService.isAdmin()) {
    return adminPage;
  } else {
    return true;
  }
}

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent, canActivate: [authGuard] },
  { path: 'register', component: RegisterComponent },
  { path: 'catalog', component: CatalogComponent, data: { admin: false }, canActivate: [authGuard] },
  { path: 'cart', component: CartComponent, data: { admin: false }, canActivate: [authGuard] },
  { path: 'admin', component: AdminComponent, data: { admin: true }, canActivate: [authGuard] },
  { path: 'admin/:id', component: AdminProductDetailComponent, data: { admin: true }, canActivate: [authGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }