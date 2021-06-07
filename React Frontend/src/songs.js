import * as React from "react";
import { useMediaQuery } from '@material-ui/core';
import {
    List,
    Datagrid,
    TextField,
    ReferenceField,
    EditButton,
    Edit,
    Create,
    SimpleForm,
    ReferenceInput,
    SelectInput,
    TextInput,
    Filter,
    SimpleList,
    ChipField, 
} from 'react-admin';

const SongFilter = (props) => (
    <Filter {...props}>
        <TextInput label="Search" source="q" alwaysOn />
        <ReferenceInput label="Artist" source="id" reference="artists" allowEmpty>
            <SelectInput optionText="name" />
        </ReferenceInput>
    </Filter>
)

export const SongList = props => {
    const isSmall = useMediaQuery(theme => theme.breakpoints.down('sm'));
    return (
        <List filters={<SongFilter />} {...props}>
            {isSmall ? (
                <SimpleList
                    primaryText={record => record.title}
                    secondaryText={record => `${record.views} views`}
                    tertiaryText={record => new Date(record.published_at).toLocaleDateString()}
                />
            ) : (
                    <Datagrid>
                        <TextField source="id" />
                        <TextField source="name" />
                        <ReferenceField label="Album" source="album.id" reference="albums">
                            <ChipField source="name" />
                        </ReferenceField>
                        <ReferenceField label="Artist" source="album.artist.id" reference="artists">
                            <ChipField source="name" />
                        </ReferenceField>
                        <EditButton />
                    </Datagrid>
                )}
        </List>
    );
}

const SongTitle = ({ record }) => {
    return <span>Song {record ? `"${record.title}"` : ''}</span>;
};

export const SongEdit = props => (
    <Edit title={<SongTitle />} {...props}>
        <SimpleForm>
            <TextInput disabled source="id" />
            <TextInput source="name" />
            <ReferenceInput source="album.id" reference="albums">
                <SelectInput optionText="name" />
            </ReferenceInput>
            <ReferenceInput source="album.artist.id" reference="artists">
                <SelectInput optionText="name" />
            </ReferenceInput>
            <ReferenceInput source="playlist.id" reference="playlists">
                <SelectInput optionText="name" />
            </ReferenceInput>
        </SimpleForm>
    </Edit>
);
export const SongCreate = props => (
    <Create {...props}>
        <SimpleForm>
            <TextInput source="name" />
            <TextInput multiline source="artist" />
            <TextInput multiline source="album" />
            <TextInput multiline source="playlist" />
        </SimpleForm>
    </Create>
);