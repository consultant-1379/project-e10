package com.backend;

import com.backend.entities.UserNode;
import com.backend.repository.DataNodeRepository;
import com.backend.entities.DataNode;
import com.backend.service.DataNodeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(BackendApplication.class, args);
		DataNodeRepository dataNodeRepository = context.getBean(DataNodeRepository.class);
		DataNodeService dataNodeService = new DataNodeService(dataNodeRepository);
		try {
			Map<String, String> responses = new HashMap<>();
			responses.put("1", "Project managers coordinate between all the different teams working on the same project, and the teams have highly specialized responsibilities.");
			responses.put("2", "Our development teams focus on achieving small, defined objectives quickly and then moving immediately to the next one.");
			responses.put("3", "A lot of up-front planning goes into documenting each step of a project before it even begins.");

			Map<String, String> responses2 = new HashMap<>();
			responses2.put("2", "Project managers coordinate between all the different teams working on the same project, and the teams have highly specialized responsibilities.");
			responses2.put("4", "A lot of up-front planning goes into documenting each step of a project before it even begins.");


			UserNode user = new UserNode();
			user.setSessionId(1234);
			DataNode node1 = new DataNode("test",responses,user);
			DataNode node2 = new DataNode("test",responses2,user);
			DataNode node3 = new DataNode("test",responses2,user);
			DataNode node4 = new DataNode("test",responses2,user);
			DataNode node5 = new DataNode("test",responses,user);
			DataNode node6 = new DataNode("test",responses,user);
			DataNode node7 = new DataNode("test",responses,user);
			DataNode node8 = new DataNode("test",responses2,user);
			DataNode node9 = new DataNode("test",responses,user);

			ArrayList<DataNode> nodes = new ArrayList<>();
			nodes.add(node1);
			nodes.add(node2);
			nodes.add(node3);
			nodes.add(node4);
			nodes.add(node5);
			nodes.add(node6);
			nodes.add(node7);
			nodes.add(node8);
			nodes.add(node9);

			dataNodeService.addList(nodes);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
