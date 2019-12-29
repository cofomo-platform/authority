package org.cofomo.authority;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedRequestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.cofomo.authority.controller.AuthenticationController;
import org.cofomo.authority.controller.AuthorizationController;
import org.cofomo.authority.facade.AuthenticationFacade;
import org.cofomo.authority.facade.AuthorizationFacade;
import org.cofomo.authority.utils.JwtDTO;
import org.cofomo.authority.utils.JwtToken;
import org.cofomo.authority.utils.RequestClaimDTO;
import org.cofomo.commons.domain.identity.Consumer;
import org.cofomo.commons.domain.identity.Credentials;
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
@WebMvcTest(AuthenticationController.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class IAuthenticationUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	JwtToken jwtToken;

	@MockBean
	private AuthenticationFacade facade;

	private String authToken = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJjb25zdW1lciIsImV4cCI6MTU3NzY2MDA5OSwiaWF0IjoxNTc3NjQyMDk5LCJjb25zdW1lciI6eyJpZCI6IjJjOTI4MDgzNmY1MTJmYWUwMTZmNTJjYmNhZjkwMDAwIiwidXNlcm5hbWUiOiJjb25zdW1lciIsInBhc3N3b3JkIjoiKioqIn19.cQZruUFWSRkhmdMaSWO_sZWGlhfflPqj3WHYsxYB97AG25SzuwmfVJghSiPixZlvAXHmjQoPg7oijcAYWRkCsA";
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(documentationConfiguration(restDocumentation).uris().withScheme("https")
						.withHost("authority.cofomo.org").withPort(443))
				.alwaysDo(
						document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
				.build();
	}

	@Test
	public void shouldReturnToken() throws Exception {

		Credentials credential = new Credentials("consumer", "consumer");
		
		JwtDTO dto = new JwtDTO(authToken);
		
		// define mock return value
		when(facade.authenticate(credential)).thenReturn(dto);
		
		// action
		this.mockMvc
				.perform(post("/v1/authenticate").content(objectMapper.writeValueAsString(credential))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andDo(document("authenticate", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
						relaxedRequestFields(
								fieldWithPath("username").description("Username of mobility consumer").type("String"),
								fieldWithPath("password").description("Password of mobility consumer").type("String")),
						responseFields(fieldWithPath("jwt").description("Json Web Token to authenticate future requests"))));
	}

}
