import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'biller',
        data: { pageTitle: 'paybillApiApp.biller.home.title' },
        loadChildren: () => import('./biller/biller.module').then(m => m.BillerModule),
      },
      {
        path: 'student',
        data: { pageTitle: 'paybillApiApp.student.home.title' },
        loadChildren: () => import('./student/student.module').then(m => m.StudentModule),
      },
      {
        path: 'customer',
        data: { pageTitle: 'paybillApiApp.customer.home.title' },
        loadChildren: () => import('./customer/customer.module').then(m => m.CustomerModule),
      },
      {
        path: 'biller-catergory',
        data: { pageTitle: 'paybillApiApp.billerCatergory.home.title' },
        loadChildren: () => import('./biller-catergory/biller-catergory.module').then(m => m.BillerCatergoryModule),
      },
      {
        path: 'contact-info',
        data: { pageTitle: 'paybillApiApp.contactInfo.home.title' },
        loadChildren: () => import('./contact-info/contact-info.module').then(m => m.ContactInfoModule),
      },
      {
        path: 'address',
        data: { pageTitle: 'paybillApiApp.address.home.title' },
        loadChildren: () => import('./address/address.module').then(m => m.AddressModule),
      },
      {
        path: 'school',
        data: { pageTitle: 'paybillApiApp.school.home.title' },
        loadChildren: () => import('./school/school.module').then(m => m.SchoolModule),
      },
      {
        path: 'payment',
        data: { pageTitle: 'paybillApiApp.payment.home.title' },
        loadChildren: () => import('./payment/payment.module').then(m => m.PaymentModule),
      },
      {
        path: 'validate-customer-by-id',
        data: { pageTitle: 'paybillApiApp.validateCustomerById.home.title' },
        loadChildren: () => import('./validate-customer-by-id/validate-customer-by-id.module').then(m => m.ValidateCustomerByIdModule),
      },
      {
        path: 'paybill',
        data: { pageTitle: 'paybillApiApp.paybill.home.title' },
        loadChildren: () => import('./paybill/paybill.module').then(m => m.PaybillModule),
      },
      {
        path: 'associated-fees',
        data: { pageTitle: 'paybillApiApp.associatedFees.home.title' },
        loadChildren: () => import('./associated-fees/associated-fees.module').then(m => m.AssociatedFeesModule),
      },
      {
        path: 'payment-channel',
        data: { pageTitle: 'paybillApiApp.paymentChannel.home.title' },
        loadChildren: () => import('./payment-channel/payment-channel.module').then(m => m.PaymentChannelModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
