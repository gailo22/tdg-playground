package com.example.functionalreactivespringdemo

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface EventRepository : ReactiveMongoRepository<Event, String>