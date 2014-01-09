/*
* The contents of this file are subject to the Mozilla Public License
* Version 1.1 (the "License"); you may not use this file except in
* compliance with the License. You may obtain a copy of the License at
* http://www.mozilla.org/MPL/ 
* 
* Software distributed under the License is distributed on an "AS IS"
* basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
* License for the specific language governing rights and limitations under
* the License.
* 
* The Original Code is OpenELIS code.
* 
* Copyright (C) The Minnesota Department of Health.  All Rights Reserved.
*/

package org.bahmni.feed.openelis.feed.service.impl;

import org.ict4h.atomfeed.server.service.Event;
import org.ict4h.atomfeed.server.service.EventService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class OpenElisUrlPublisherTest {
    private final String SAMPLE_UUID = "GAN12345";

    @Mock
    EventService eventService;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void shouldConstructUrlIdentifierAndProperties() {
        OpenElisUrlPublisher publisher = new OpenElisUrlPublisher(eventService, CATEGORY, PATIENT_TITLE);

        publisher.publish(SAMPLE_UUID, "/openelis");

        ArgumentCaptor<Event> captor = ArgumentCaptor.forClass(Event.class);
        verify(eventService).notify(captor.capture());
        Event event = captor.getValue();
        assertEquals("/openelis/ws/rest/patient/" + SAMPLE_UUID, event.getContents());
    }

    @Test
    public void shouldPopulateDefaultFieldsWhenCallingEventService() {
        OpenElisUrlPublisher publisher = new OpenElisUrlPublisher(eventService, CATEGORY, PATIENT_TITLE);

        publisher.publish(SAMPLE_UUID, "/openelis");

        ArgumentCaptor<Event> captor = ArgumentCaptor.forClass(Event.class);
        verify(eventService).notify(captor.capture());
        Event event = captor.getValue();
        assertNotNull(event.getUuid());
        assertFalse(event.getUuid().isEmpty());
        assertEquals("patient", event.getCategory());
        assertEquals(null, event.getUri());
        assertNotNull(event.getTimeStamp());
        assertEquals("openelis",event.getTitle());
    }
}
