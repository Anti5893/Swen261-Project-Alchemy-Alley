import { NgModule, inject } from '@angular/core';
import { Routes, RouterModule, CanActivateFn, ActivatedRouteSnapshot,
         RouterStateSnapshot, createUrlTreeFromSnapshot, UrlTree } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { CatalogComponent } from './catalog/catalog.component';
import { CartComponent } from './cart/cart.component';
import { AdminComponent } from './admin/admin.component';
import { CredentialsService } from './credentials.service';

const authGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot, 
                                    credentialsService = inject(CredentialsService)) => {
  const loginPage: UrlTree = createUrlTreeFromSnapshot(route, ['/login']);
  const catalogPage: UrlTree = createUrlTreeFromSnapshot(route, ['/catalog']);
  const adminPage: UrlTree = createUrlTreeFromSnapshot(route, ['/admin']);

  if(route.data?.['auth'] && !credentialsService.isLoggedIn()) {
    // Page requires authentication
    return loginPage;
  } else if(!route.data?.['auth'] && credentialsService.isLoggedIn()) {
    // Need to logout first to access the page
    return !credentialsService.isAdmin() ? catalogPage : adminPage;
  } else if(route.data?.['admin'] && !credentialsService.isAdmin()) {
    // Requires admin permissions
    return catalogPage;
  } else if(!route.data?.['admin'] && credentialsService.isAdmin()) {
    // Requires buyer permissions
    return adminPage;
  } else {
    // Page is authorized
    return true;
  }
}

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent, data: { auth: false, admin: false }, canActivate: [authGuard] },
  { path: 'register', component: RegisterComponent, data: { auth: false, admin: false }, canActivate: [authGuard] },
  { path: 'catalog', component: CatalogComponent, data: { auth: true, admin: false }, canActivate: [authGuard] },
  { path: 'cart', component: CartComponent, data: { auth: true, admin: false }, canActivate: [authGuard] },
  { path: 'admin', component: AdminComponent, data: { auth: true, admin: true }, canActivate: [authGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }