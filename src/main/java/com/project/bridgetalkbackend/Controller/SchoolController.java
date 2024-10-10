package com.project.bridgetalkbackend.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bridgetalkbackend.domain.Schools;
import com.project.bridgetalkbackend.repository.SchoolsRepository;
import com.project.bridgetalkbackend.vo.SchoolListVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class SchoolController {
    private final SchoolsRepository schoolsRepository;

    public SchoolController(SchoolsRepository schoolsRepository) {
        this.schoolsRepository = schoolsRepository;
    }

    // db 저장
    @GetMapping("/schoolList")
    public void getSchoolList() {
        HttpURLConnection urlConnection = null;
        InputStream stream = null;
        String result = null;
        try {
            URL url = new URL("https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=3a4a17961ccbad952be7b263ff0bc076&svcType=api&svcCode=SCHOOL&contentType=json&gubun=univ_list&perPage=1000");
            urlConnection = (HttpURLConnection) url.openConnection();
            stream = getNetworkConnection(urlConnection);
            result = readStreamToString(stream);
            SchoolListVO schoolListVO = jsonParsing(result);
            for(var school : schoolListVO.getDataSearch().getContent()){
                Schools schools = new Schools();
                schools.setSchoolName(school.getSchoolName());
                if(!schoolsRepository.existsBySchoolName(schools.getSchoolName())){
                    schoolsRepository.save(schools);
                }
            }
            if (stream != null) stream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SchoolListVO jsonParsing(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SchoolListVO  schoolListVO = mapper.readValue(json, SchoolListVO.class);
        return schoolListVO;
    }

    /* URLConnection 을 전달받아 연결정보 설정 후 연결, 연결 후 수신한 InputStream 반환 */

    private InputStream getNetworkConnection(HttpURLConnection urlConnection) throws IOException {
        urlConnection.setConnectTimeout(3000);
        urlConnection.setReadTimeout(3000);
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoInput(true);

        if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code : " + urlConnection.getResponseCode());
        }

        return urlConnection.getInputStream();
    }

    /* InputStream을 전달받아 문자열로 변환 후 반환 */
    private String readStreamToString(InputStream stream) throws IOException {
        StringBuilder result = new StringBuilder();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        String readLine;
        while ((readLine = br.readLine()) != null) {
            result.append(readLine + "\n\r");
        }

        br.close();

        return result.toString();
    }
}