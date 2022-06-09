package com.alcideswenner.app_wildfly.resouces;

import com.alcideswenner.app_wildfly.CRM.CRMService;
import com.alcideswenner.app_wildfly.entities.Contato;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/contacts")
public class ContatoRS {
        @EJB
        private CRMService crmService;

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public List<Contato> getContacts() {
            return crmService.getAllContacts().stream().map( ce ->
                    new Contato(ce.getContactId(), ce.getFirstName(), ce.getLastName(), ce.getCompanyName())
            ).collect(Collectors.toList());
        }

        @GET
        @Path("/{id}")
        @Produces(MediaType.APPLICATION_JSON)
        public Contato getContact(@PathParam("id") Long id) {
            System.out.println("detalhe");
            return crmService
                    .findContact(id)
                    .map(
                            entity -> new Contato(entity.getContactId(), entity.getFirstName(), entity.getLastName(), entity.getCompanyName())
                    )
                    .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
        }

        @PUT
        @Path("/{id}")
        @Consumes(MediaType.APPLICATION_JSON)
        public void updateContact(@PathParam("id") Long id, Contato c) {

            crmService
                    .findContact(id)
                    .flatMap(
                            ce -> {
                                ce.setFirstName( c.getFirstName() );
                                ce.setLastName( c.getLastName() );
                                ce.setCompanyName( c.getCompanyName() );

                                crmService.updateContact(ce);

                                return Optional.of(ce);
                            }
                    )
                    .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
        }

        @DELETE
        @Path("/{id}")
        public void deleteContact(@PathParam("id") Long id) { crmService.deleteContact(id); }

        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Contato addContact(Contato ce) {
            Contato fromDB = crmService.addContact(ce);

            return fromDB;
        }
    }
