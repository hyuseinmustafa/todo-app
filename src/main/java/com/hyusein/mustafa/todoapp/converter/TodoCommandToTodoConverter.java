package com.hyusein.mustafa.todoapp.converter;

import com.hyusein.mustafa.todoapp.command.TodoCommand;
import com.hyusein.mustafa.todoapp.model.Todo;
import org.springframework.core.convert.converter.Converter;

public class TodoCommandToTodoConverter implements Converter<TodoCommand, Todo> {
    @Override
    public Todo convert(TodoCommand source) {
        Todo target = new Todo();
        target.setId(source.getId());
        target.setHeadline(source.getHeadline());
        target.setDescription(source.getDescription());
        target.setStatus(source.getStatus());
        return target;
    }
}
