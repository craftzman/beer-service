import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class BeerList extends Component {

    constructor(props) {
        super(props);
        this.state = {beers: []};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        fetch('api/beers')
            .then(response => response.json())
            .then(data => this.setState({beers: data}));
    }

    async remove(id) {
        await fetch(`api/beers/${id}`, {
            method: 'DELETE',
            headers: {
               'Accept': 'application/json',
               'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedBeers = [...this.state.beers].filter(i => i.id !== id);
            this.setState({beers: updatedBeers});
        });
    }

    render() {
            const {beers} = this.state;

            const beerList = beers.map(beer => {
                return <tr key={beer.id}>
                    <td style={{whiteSpace: 'nowrap'}}>{beer.name}</td>
                    <td>type</td>
                    <td>
                        <ButtonGroup>
                            <Button size="sm" color="primary" tag={Link} to={"/api/beers/" + beer.id}>Edit</Button>
                            <Button size="sm" color="danger" onClick={() => this.remove(beer.id)}>Delete</Button>
                        </ButtonGroup>
                    </td>
                </tr>
            });

            return (
                <div>
                    <AppNavbar/>
                    <Container fluid>
                        <div className="float-right">
                            <Button color="success" tag={Link} to="/beers/new">Add Beer</Button>
                        </div>
                        <h3>Beers</h3>
                        <Table className="mt-4">
                            <thead>
                            <tr>
                                <th width="30%">Name</th>
                                <th width="30%">Type</th>
                                <th width="40%">Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                                {beerList}
                            </tbody>
                        </Table>
                    </Container>
                </div>
            );
        }
}
export default BeerList;