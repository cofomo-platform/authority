package org.cofomo.authority;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.cofomo.authority.controller.AuthorizationController;
import org.cofomo.authority.facade.AuthorizationFacade;
import org.cofomo.authority.utils.JwtDTO;
import org.cofomo.authority.utils.JwtToken;
import org.cofomo.authority.utils.RequestClaimDTO;
import org.cofomo.commons.domain.identity.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
@WebMvcTest(AuthorizationController.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class IAuthorizationUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	JwtToken jwtToken;

	@MockBean
	private AuthorizationFacade facade;

	private String authToken = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJpbGtvIiwiZXhwIjoxNTc2NjkwMjEwLCJpYXQiOjE1NzY2NzIyMTAsImNvbnN1bWVyIjp7ImlkIjoiMSIsInVzZXJuYW1lIjoiaWxrbyIsInBhc3N3b3JkIjoiKioqIn19.XwxG_wwllis3-ez8xdJtiL-2ZIfAVnxIvZ2A9MqksM_Iqaz_Pr3YPVD42t2y1xzqV5VL4Jp_2TKR9Q2NsUkOeQ";
	private String claimToken = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ4TXBDT0tDNUk0SU56RkNhYjNXRW13PT0iLCJ2Y3RfdGVzdCI6eyJjb250ZXh0IjoiaHR0cHM6Ly93d3cudzMub3JnLzIwMTgvY3JlZGVudGlhbHMvdjEiLCJ0eXBlIjoidmN0X3Rlc3QiLCJvd25lciI6InhNcENPS0M1STRJTnpGQ2FiM1dFbXc9PSIsImlzc3VlciI6IkF1dGhvcml0eSBvZiB2Y3RfdGVzdCIsInZhbGlkYXRpb24iOiIiLCJpc3N1YW5jZURhdGUiOiIyMDE5LTEyLTE4VDEwOjM5OjQzKzAwMDAifSwiZXhwIjoxNTc2NjgzNTgzLCJpYXQiOjE1NzY2NjU1ODN9.PyeXlc6xzk9pGvRQRajYcpB_cTalT9MmxG7jWi7KA2qRF32OxEngcbAVxiQaVQmLrDAALkwbUC1i1EObuhd96g";

	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(documentationConfiguration(restDocumentation).uris().withScheme("https")
						.withHost("authority.cofomo.io/api").withPort(443))
				.alwaysDo(
						document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
				.build();
	}

	@Test
	public void shouldReturnRequestedClaimToken() throws Exception {

		String vct = "vct_has_driversLicense";
		RequestClaimDTO requestClaimDto = new RequestClaimDTO(vct, authToken);
		JwtDTO jwtClaimDto = new JwtDTO(claimToken);
		
		// define mock return value
		when(facade.requestClaimToken(authToken, vct)).thenReturn(jwtClaimDto);
		
		// action
		this.mockMvc
				.perform(post("/v1/authorization/requestClaim").content(objectMapper.writeValueAsString(requestClaimDto))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isCreated())
				.andDo(document("request-claim", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
						requestFields(fieldWithPath("vct").description("Type of VerifiableClaim"),
								fieldWithPath("jwt")
										.description("Json Web Token to authenticate the mobility consumer")),
						responseFields(fieldWithPath("jwt").description("Json Web Token"))));
	}

	@Test
	public void shouldValidateClaimToken() throws Exception {

		JwtDTO jwtClaimDto = new JwtDTO(claimToken);
		
		// define mock return value
		when(facade.validateClaim(jwtClaimDto.getJwt())).thenReturn(true);

		// action
		this.mockMvc
				.perform(post("/v1/authorization/validateClaim").content(objectMapper.writeValueAsString(jwtClaimDto))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andDo(document("validate-claim", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
						requestFields(fieldWithPath("jwt").description("Json Claim Token"))));
	}

}
