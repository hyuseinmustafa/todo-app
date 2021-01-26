package com.hyusein.mustafa.todoapp.converter;

import com.hyusein.mustafa.todoapp.command.TodoCommand;
import com.hyusein.mustafa.todoapp.model.Todo;
import org.springframework.core.convert.converter.Converter;

public class TodoToTodoCommandConverter implements Converter<Todo, TodoCommand> {
    @Override
    public TodoCommand convert(Todo source) {
        if(source == null)return null;

        TodoCommand target = new TodoCommand();
        target.setId(source.getId());
        target.setHeadline(source.getHeadline());
        target.setDescription(source.getDescription());
        target.setStatus(source.getStatus());
        return target;
    }
}
