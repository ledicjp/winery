import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { AccordionModule } from 'ngx-bootstrap/accordion';
import { JsPlumbService } from './jsPlumbService';

import { AppComponent } from './app.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { WineryAlertModule } from './winery-alert/winery-alert.module';
import { ToastModule, ToastOptions } from 'ng2-toastr/ng2-toastr';
import { WineryCustomOption } from './winery-alert/winery-alert-options';
import { PaletteComponent } from './palette/palette.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import {JsonService} from './jsonService/json.service';
import { TopologyRendererModule } from './topology-renderer/topology-renderer.module';
import { PropertiesComponent } from './properties/properties.component';
import { DeploymentArtifactsComponent } from './deployment-artifacts/deployment-artifacts.component';
import { RequirementsCapabilitiesComponent } from './requirements-capabilities/requirements-capabilities.component';
import { PoliciesComponent } from './policies/policies.component';
import { PrintViewComponent } from './print-view/print-view.component';
import { TargetLocationsComponent } from './target-locations/target-locations.component';
import {NgReduxModule, NgRedux, DevToolsExtension} from '@angular-redux/store';
import {AppState, INITIAL_APP_STATE, paletteRootReducer} from './redux/store/app.store';
import {PaletteActions} from './redux/actions/palette.actions';
import {ButtonActions} from './redux/actions/topologyRenderer.actions';

@NgModule({
  declarations: [
    AppComponent,
    PaletteComponent,
    SidebarComponent,
    PropertiesComponent,
    DeploymentArtifactsComponent,
    RequirementsCapabilitiesComponent,
    PoliciesComponent,
    PrintViewComponent,
    TargetLocationsComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    BrowserAnimationsModule,
    NgReduxModule,
    BsDropdownModule.forRoot(),
    WineryAlertModule.forRoot(),
    ToastModule.forRoot(),
    AccordionModule.forRoot(),
    TopologyRendererModule.forRoot()
  ],
  providers: [
    {provide: ToastOptions, useClass: WineryCustomOption},
    JsPlumbService,
    JsonService,
    PaletteActions,
    ButtonActions
  ],

  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(ngRedux: NgRedux<AppState>,
              devTools: DevToolsExtension) {
    const storeEnhancers = devTools.isEnabled() ?
      [ devTools.enhancer() ] :
      [];

    ngRedux.configureStore(
      paletteRootReducer,
      INITIAL_APP_STATE,
      [],
      storeEnhancers);
  }
}
