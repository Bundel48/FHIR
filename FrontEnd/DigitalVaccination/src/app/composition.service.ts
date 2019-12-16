import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { PatientService } from './patient.service';
import { PractitionerService} from './practitioner.service';
import { OrganizationService} from './organization.service';
import { ImmunizationService } from './immunization.service';
import { ConditionService } from './condition.service';
import { EncounterService } from './encounter.service';
import { ObservationService } from './observation.service';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CompositionService {
  public compositionData: Promise<any>;
  API_URL: string = "https://funke.imi.uni-luebeck.de/public/fhir/";
  constructor(
    private httpClient: HttpClient,
    private patientService: PatientService,
    private immunizationService: ImmunizationService,
    private practitionerService: PractitionerService,
    private organizationService: OrganizationService,
    private encounterService: EncounterService,
    private conditionService: ConditionService,
    private observationService: ObservationService
  ) {
    this.compositionData = this.getComposition(146836).toPromise();
  }

  getComposition(id : number) {
    return this.httpClient.get(this.API_URL + 'Composition/' + id).pipe(
      map(async data => {
        var obj:any = data;
/*------------------------get Patient information from subject Tag of JSON Composition---------------------------------*/
        obj.subject = await this.patientService.getPatient(this.getIdFromReference(obj.subject.reference)).toPromise();

/*------------------------get medical information from section tag of JSON Composition---------------------------------*/
        for (let i = 0; i < obj.section.length; i++) {
/*------------------------get immunization information from section tag of JSON Composition---------------------------------*/
          if (obj.section[i].title === 'Standardimpfungen') {
            if(typeof obj.section[i].entry !== 'undefined'){
            /* Get Immunizations*/
              obj.standardimpfung = {"entry": []};
              for (let j = 0; j < obj.section[i].entry.length; j++) {
                obj.standardimpfung.entry[j] = await this.immunizationService.getImmunization(this.getIdFromReference(obj.section[i].entry[j].reference)).toPromise();
                /*Get encounter of immunization*/
                obj.standardimpfung.entry[j].encounter = await this.encounterService.getEncounter(this.getIdFromReference(obj.standardimpfung.entry[j].encounter.reference)).toPromise();
                /*Get practitioner of encounter */
                obj.standardimpfung.entry[j].encounter.participant = await this.practitionerService.getPractitioner(this.getIdFromReference(obj.standardimpfung.entry[j].encounter.participant[0].individual.reference)).toPromise();
              /*Get Organization of encounter*/
                obj.standardimpfung.entry[j].encounter.serviceProvider = await this.organizationService.getOrganization(this.getIdFromReference(obj.standardimpfung.entry[j].encounter.serviceProvider.reference)).toPromise();
              }
             }

/*------------------------get YellowFever information from section tag of JSON Composition---------------------------------*/
          } else if (obj.section[i].title === 'Gelbfieber') {
          /* Get Immunizations*/
            obj.yellowfever = {"entry": []};
            if(typeof obj.section[i].entry !== 'undefined'){
              for (let j = 0; j < obj.section[i].entry.length; j++) {
                obj.yellowfever.entry[j] = await this.immunizationService.getImmunization(this.getIdFromReference(obj.section[i].entry[j].reference)).toPromise();
                /*Get encounter of immunization*/
                obj.yellowfever.entry[j].encounter = await this.encounterService.getEncounter(this.getIdFromReference(obj.yellowfever.entry[j].encounter.reference)).toPromise();
                /*Get practitioner of encounter */
                obj.yellowfever.entry[j].encounter.participant = await this.practitionerService.getPractitioner(this.getIdFromReference(obj.yellowfever.entry[j].encounter.participant[0].individual.reference)).toPromise();
              /*Get Organization of encounter*/
                obj.yellowfever.entry[j].encounter.serviceProvider = await this.organizationService.getOrganization(this.getIdFromReference(obj.yellowfever.entry[j].encounter.serviceProvider.reference)).toPromise();
              }
             }
/*------------------------get Other vaccination information from section tag of JSON Composition---------------------------------*/
          } else if (obj.section[i].title === 'Sonstige Schutzimpfungen') {
          /* Get Immunizations*/
            obj.others = {"entry": []};
            if(typeof obj.section[i].entry !== 'undefined'){
              for (let j = 0; j < obj.section[i].entry.length; j++) {
                obj.others.entry[j] = await this.immunizationService.getImmunization(this.getIdFromReference(obj.section[i].entry[j].reference)).toPromise();
                /*Get encounter of immunization*/
                obj.others.entry[j].encounter = await this.encounterService.getEncounter(this.getIdFromReference(obj.others.entry[j].encounter.reference)).toPromise();
                /*Get practitioner of encounter */
                obj.others.entry[j].encounter.participant = await this.practitionerService.getPractitioner(this.getIdFromReference(obj.others.entry[j].encounter.participant[0].individual.reference)).toPromise();
              /*Get Organization of encounter*/
                obj.others.entry[j].encounter.serviceProvider = await this.organizationService.getOrganization(this.getIdFromReference(obj.others.entry[j].encounter.serviceProvider.reference)).toPromise();
              }
             }
/*------------------------get influenza vaccination information from section tag of JSON Composition---------------------------------*/
          } else if (obj.section[i].title === 'Schutzimpfungen gegen Influenza') {
          /* Get Immunizations*/
            obj.influenz = {"entry": []};
            if(typeof obj.section[i].entry !== 'undefined'){
              for (let j = 0; j < obj.section[i].entry.length; j++) {
                obj.influenz.entry[j] = await this.immunizationService.getImmunization(this.getIdFromReference(obj.section[i].entry[j].reference)).toPromise();
                /*Get encounter of immunization*/
                obj.influenz.entry[j].encounter = await this.encounterService.getEncounter(this.getIdFromReference(obj.influenz.entry[j].encounter.reference)).toPromise();
                /*Get practitioner of encounter */
                obj.influenz.entry[j].encounter.participant = await this.practitionerService.getPractitioner(this.getIdFromReference(obj.influenz.entry[j].encounter.participant[0].individual.reference)).toPromise();
              /*Get Organization of encounter*/
                obj.influenz.entry[j].encounter.serviceProvider = await this.organizationService.getOrganization(this.getIdFromReference(obj.influenz.entry[j].encounter.serviceProvider.reference)).toPromise();
              }
             }


/*------------------------get humanSerum information from section tag of JSON Composition---------------------------------*/
          } else if (obj.section[i].title === 'Passive Immunisierungen mit humanen Immunoglobulinen') {
            /* Get Immunizations*/
            obj.human = {"entry": []};
            if(typeof obj.section[i].entry !== 'undefined'){
              for (let j = 0; j < obj.section[i].entry.length; j++) {
                obj.human.entry[j] = await this.immunizationService.getImmunization(this.getIdFromReference(obj.section[i].entry[j].reference)).toPromise();
                /*Get encounter of immunization*/
                obj.human.entry[j].encounter = await this.encounterService.getEncounter(this.getIdFromReference(obj.human.entry[j].encounter.reference)).toPromise();
                /*Get practitioner of encounter */
                obj.human.entry[j].encounter.participant = await this.practitionerService.getPractitioner(this.getIdFromReference(obj.human.entry[j].encounter.participant[0].individual.reference)).toPromise();
              /*Get Organization of encounter*/
                obj.human.entry[j].encounter.serviceProvider = await this.organizationService.getOrganization(this.getIdFromReference(obj.human.entry[j].encounter.serviceProvider.reference)).toPromise();
              }
             }
/*------------------------get animalSerum information from section tag of JSON Composition---------------------------------*/
          } else if (obj.section[i].title === 'Serum-Injektion') {
            /* Get Immunizations*/
            obj.serum = {"entry": []};
            if(typeof obj.section[i].entry !== 'undefined'){
              for (let j = 0; j < obj.section[i].entry.length; j++) {
                obj.serum.entry[j] = await this.immunizationService.getImmunization(this.getIdFromReference(obj.section[i].entry[j].reference)).toPromise();
                /*Get encounter of immunization*/
                obj.serum.entry[j].encounter = await this.encounterService.getEncounter(this.getIdFromReference(obj.serum.entry[j].encounter.reference)).toPromise();
                /*Get practitioner of encounter */
                obj.serum.entry[j].encounter.participant = await this.practitionerService.getPractitioner(this.getIdFromReference(obj.serum.entry[j].encounter.participant[0].individual.reference)).toPromise();
              /*Get Organization of encounter*/
                obj.serum.entry[j].encounter.serviceProvider = await this.organizationService.getOrganization(this.getIdFromReference(obj.serum.entry[j].encounter.serviceProvider.reference)).toPromise();
              }
             }
/*------------------------get risk information from section tag of JSON Composition---------------------------------*/
          } else if (obj.section[i].title === 'Ärztliche Vermerke über medizinische Risikofaktoren bei Impfungen') {
            /* Get condition*/
            obj.risk = {"entry": []};
            if(typeof obj.section[i].entry !== 'undefined'){
              for (let j = 0; j < obj.section[i].entry.length; j++) {
                obj.risk.entry[j]= await this.conditionService.getCondition(this.getIdFromReference(obj.section[i].entry[j].reference)).toPromise();
              }
             }

/*------------------------get rh-factor information from section tag of JSON Composition---------------------------------*/
         } else if (obj.section[i].title === 'Blutgruppe und Rh-Faktor') {
           /* TODOr*/
           obj.rh = {"entry": []};
           if(typeof obj.section[i].entry !== 'undefined'){
            for (let j = 0; j < obj.section[i].entry.length; j++) {
              obj.rh.entry[j] = await this.observationService.getObservation(this.getIdFromReference(obj.section[i].entry[j].reference)).toPromise();
             /*Get encounter of observation*/
              obj.rh.entry[j].encounter = await this.encounterService.getEncounter(this.getIdFromReference(obj.rh.entry[j].encounter.reference)).toPromise();
              /*Get practitioner of encounter */
              obj.rh.entry[j].encounter.participant = await this.practitionerService.getPractitioner(this.getIdFromReference(obj.rh.entry[j].encounter.participant[0].individual.reference)).toPromise();
            /*Get Organization of encounter*/
              obj.rh.entry[j].encounter.serviceProvider = await this.organizationService.getOrganization(this.getIdFromReference(obj.rh.entry[j].encounter.serviceProvider.reference)).toPromise();
            }
          }
/*------------------------get antibody information from section tag of JSON Composition---------------------------------*/
         } else if (obj.section[i].title === 'Ergebnisse von Antikörperuntersuchungen') {
           /* TODO antibody*/
           obj.antibody = {"entry": []};
          if(typeof obj.section[i].entry !== 'undefined'){
            for (let j = 0; j < obj.section[i].entry.length; j++) {
              obj.antibody.entry[j] = await this.observationService.getObservation(this.getIdFromReference(obj.section[i].entry[j].reference)).toPromise();
             /*Get encounter of observation*/
              obj.antibody.entry[j].encounter = await this.encounterService.getEncounter(this.getIdFromReference(obj.antibody.entry[j].encounter.reference)).toPromise();
              /*Get practitioner of encounter */
              obj.antibody.entry[j].encounter.participant = await this.practitionerService.getPractitioner(this.getIdFromReference(obj.antibody.entry[j].encounter.participant[0].individual.reference)).toPromise();
            /*Get Organization of encounter*/
              obj.antibody.entry[j].encounter.serviceProvider = await this.organizationService.getOrganization(this.getIdFromReference(obj.antibody.entry[j].encounter.serviceProvider.reference)).toPromise();
            }
           }


/*------------------------get TBC vaccination information from section tag of JSON Composition---------------------------------*/
         } else if (obj.section[i].title === 'Bescheinigung über Tuberkulose-Schutzimpfungen (BCG)') {
         /* Get Immunizations*/
           obj.tubvac = {"entry": []};
           if(typeof obj.section[i].entry !== 'undefined'){
             for (let j = 0; j < obj.section[i].entry.length; j++) {
               obj.tubvac.entry[j] = await this.immunizationService.getImmunization(this.getIdFromReference(obj.section[i].entry[j].reference)).toPromise();
               /*Get encounter of immunization*/
               obj.tubvac.entry[j].encounter = await this.encounterService.getEncounter(this.getIdFromReference(obj.tubvac.entry[j].encounter.reference)).toPromise();
               /*Get practitioner of encounter */
               obj.tubvac.entry[j].encounter.participant = await this.practitionerService.getPractitioner(this.getIdFromReference(obj.tubvac.entry[j].encounter.participant[0].individual.reference)).toPromise();
             /*Get Organization of encounter*/
               obj.tubvac.entry[j].encounter.serviceProvider = await this.organizationService.getOrganization(this.getIdFromReference(obj.tubvac.entry[j].encounter.serviceProvider.reference)).toPromise();
             }
            }
/*------------------------get tuberkulin information from section tag of JSON Composition---------------------------------*/
         } else if (obj.section[i].title === 'Ergebnis von Tuberkulinproben') {

           obj.tub = {"entry": []};
           if(typeof obj.section[i].entry !== 'undefined'){
             for (let j = 0; j < obj.section[i].entry.length; j++) {
               obj.tub.entry[j] = await this.observationService.getObservation(this.getIdFromReference(obj.section[i].entry[j].reference)).toPromise();
              /*Get encounter of observation*/
               obj.tub.entry[j].encounter = await this.encounterService.getEncounter(this.getIdFromReference(obj.tub.entry[j].encounter.reference)).toPromise();
               /*Get practitioner of encounter */
               obj.tub.entry[j].encounter.participant = await this.practitionerService.getPractitioner(this.getIdFromReference(obj.tub.entry[j].encounter.participant[0].individual.reference)).toPromise();
             /*Get Organization of encounter*/
               obj.tub.entry[j].encounter.serviceProvider = await this.organizationService.getOrganization(this.getIdFromReference(obj.tub.entry[j].encounter.serviceProvider.reference)).toPromise();
             }
            }
/*------------------------get Hepatitis Test from section tag of JSON Composition---------------------------------*/
         } else if (obj.section[i].title === 'Virushepatitis B') {

           obj.hep = {"entry": []};
           if(typeof obj.section[i].entry !== 'undefined'){

             for (let j = 0; j < obj.section[i].entry.length; j++) {
               obj.hep.entry[j] = await this.observationService.getObservation(this.getIdFromReference(obj.section[i].entry[j].reference)).toPromise();
              /*Get encounter of observation*/
               obj.hep.entry[j].encounter = await this.encounterService.getEncounter(this.getIdFromReference(obj.hep.entry[j].encounter.reference)).toPromise();
               /*Get practitioner of encounter */
               obj.hep.entry[j].encounter.participant = await this.practitionerService.getPractitioner(this.getIdFromReference(obj.hep.entry[j].encounter.participant[0].individual.reference)).toPromise();
             /*Get Organization of encounter*/
               obj.hep.entry[j].encounter.serviceProvider = await this.organizationService.getOrganization(this.getIdFromReference(obj.hep.entry[j].encounter.serviceProvider.reference)).toPromise();
             }
            }
/*------------------------get r-antibody Test from section tag of JSON Composition---------------------------------*/
         } else if (obj.section[i].title === 'Röteln-Antikörper-Bestimmungen') {

           obj.rbody = {"entry": []};
           if(typeof obj.section[i].entry !== 'undefined'){
             for (let j = 0; j < obj.section[i].entry.length; j++) {
               obj.rbody.entry[j] = await this.observationService.getObservation(this.getIdFromReference(obj.section[i].entry[j].reference)).toPromise();
              /*Get encounter of observation*/
               obj.rbody.entry[j].encounter = await this.encounterService.getEncounter(this.getIdFromReference(obj.rbody.entry[j].encounter.reference)).toPromise();
               /*Get pracrbodytitioner of encounter */
               obj.rbody.entry[j].encounter.participant = await this.practitionerService.getPractitioner(this.getIdFromReference(obj.rbody.entry[j].encounter.participant[0].individual.reference)).toPromise();
             /*Get Organization of encounter*/
               obj.rbody.entry[j].encounter.serviceProvider = await this.organizationService.getOrganization(this.getIdFromReference(obj.rbody.entry[j].encounter.serviceProvider.reference)).toPromise();
             }
            }



         }

        }

        return data;
      })
    );
  }

  private getIdFromReference(reference: string) : number {
    let paths = reference.split('/');
    return parseInt(paths[paths.length - 1]);
  }
}
