package com.example.Student_Course_Registration_System.controller;

import com.example.Student_Course_Registration_System.model.Room;
import com.example.Student_Course_Registration_System.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/rooms")
    public String getAllRooms(Model model, HttpSession session) {
        model.addAttribute("userRole", session.getAttribute("userRole"));
        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("userId", session.getAttribute("userId"));
        model.addAttribute("rooms", roomService.getAllRooms());
        return "room";
    }

    @PostMapping("/rooms/add")
    public String addRoom(
            @RequestParam String section,
            @RequestParam String roomNumber,
            @RequestParam int capacity,
            @RequestParam boolean available) {

        String roomId = section.toUpperCase() + roomNumber;
        String roomName = "Room " + roomId;
        Room room = new Room(roomId, roomName, capacity);
        room.setAvailable(available);
        roomService.addRoom(room);
        return "redirect:/rooms";
    }

    @PostMapping("/rooms/edit/{roomId}")
    public String updateRoom(
            @PathVariable String roomId,
            @RequestParam String roomName,
            @RequestParam int capacity,
            @RequestParam boolean available) {

        Room room = new Room(roomId, roomName, capacity);
        room.setAvailable(available);
        roomService.updateRoom(room);
        return "redirect:/rooms";
    }

    @GetMapping("/rooms/delete/{roomId}")
    public String deleteRoom(@PathVariable String roomId) {
        roomService.deleteRoom(roomId);
        return "redirect:/rooms";
    }

    @GetMapping("/rooms/available")
    public String getAvailableRooms(Model model, HttpSession session) {
        model.addAttribute("userRole", session.getAttribute("userRole"));
        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("userId", session.getAttribute("userId"));
        model.addAttribute("rooms", roomService.getAvailableRooms());
        return "room";
    }

    @GetMapping("/rooms/search")
    public String searchRoom(@RequestParam String name, Model model, HttpSession session) {
        model.addAttribute("userRole", session.getAttribute("userRole"));
        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("userId", session.getAttribute("userId"));
        model.addAttribute("rooms", roomService.searchByName(name));
        return "room";
    }
}