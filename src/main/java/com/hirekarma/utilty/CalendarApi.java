package com.hirekarma.utilty;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
//import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.ConferenceData;
import com.google.api.services.calendar.model.ConferenceSolutionKey;
import com.google.api.services.calendar.model.CreateConferenceRequest;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;
import com.hirekarma.controller.AdminController;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* class to demonstarte use of Calendar events list API */
public class CalendarApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
    /** Application name. */
    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /** Directory to store authorization tokens for this application. */
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = CalendarApi.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
      
        //returns an authorized Credential object.
        return credential;
    }

    public static void main(String args[]) {
    	try {
			System.out.println(insert("TESTING", "KJDFKJADFKLJAKLJKLSDJFKLJ", "Asia", 2, List.of("sawant.rohit510@gmail.com","rohitsadeepsawant@gmail.com"), "2022-06-30T09:00:00", "2022-06-30T09:30:00"));
		} catch (GeneralSecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public static String insert(String eventName,String description,String location,int noOfAttendees,List<String> attendees,String startTime,String endTime) throws GeneralSecurityException, IOException {
    	final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    	LOGGER.info("creating eveent");
    	Event event = new Event()
        	    .setSummary(eventName)
        	    .setLocation(location)
        	    .setDescription(description);

        	DateTime startDateTime = new DateTime(startTime);
        	EventDateTime start = new EventDateTime()
        	    .setDateTime(startDateTime)
        	    .setTimeZone("Asia/Kolkata");
        	event.setStart(start);

        	DateTime endDateTime = new DateTime(endTime);
        	EventDateTime end = new EventDateTime()
        	    .setDateTime(endDateTime)
        	    .setTimeZone("Asia/Kolkata");
        	event.setEnd(end);

//        	setting attendees
        	List<EventAttendee> attendees1 = new ArrayList<>();
        	for(int i=0;i<noOfAttendees;i++)
        	{
        		attendees1.add(new EventAttendee().setEmail(attendees.get(i)));
        	}
        	event.setAttendees(attendees1);

        	EventReminder[] reminderOverrides = new EventReminder[] {
        	    new EventReminder().setMethod("email").setMinutes(24 * 60),
        	    new EventReminder().setMethod("popup").setMinutes(10),
        	};

//        	for meet
        	ConferenceSolutionKey conferenceSKey = new ConferenceSolutionKey();
        	conferenceSKey.setType("hangoutsMeet");  // Non-G suite user
        	CreateConferenceRequest createConferenceReq = new CreateConferenceRequest();
        	createConferenceReq.setRequestId("3whatisup3"); // ID generated by you
        	createConferenceReq.setConferenceSolutionKey(conferenceSKey);
        	ConferenceData conferenceData = new ConferenceData();
        	conferenceData.setCreateRequest(createConferenceReq);
        	event.setConferenceData(conferenceData);
//        	end meet
        	Event.Reminders reminders = new Event.Reminders()
        	    .setUseDefault(false)
        	    .setOverrides(Arrays.asList(reminderOverrides));
        	event.setReminders(reminders);

        	String calendarId = "primary";
LOGGER.info("inserting event");
        	event = service.events().insert(calendarId, event).execute();
        	
        	return "Event Created: "+event.getHtmlLink()+" hangout: "+event.getHangoutLink() ;
    }
}
