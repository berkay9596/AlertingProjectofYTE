import React, {Component} from 'react';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import axios from 'axios';


class AddButton extends Component {
    state = {
        alertName: "",
        url: "",
        httpMethod: "GET",
        period: 0
    };
    nameHandler = (e) => {
        this.setState({alertName: e.target.value});
    };

    urlHandler = (e) => {
        this.setState({url: e.target.value});
    };

    httpHandler = (e) => {
        this.setState({httpMethod: e.target.value});
    };

    periodHandler = (e) => {
        this.setState({period: e.target.value});
    };

    handleSubmit = event => {
        event.preventDefault();

        const user = {
            alertName: this.state.alertName,
            url: this.state.url,
            httpMethod: this.state.httpMethod,
            period: this.state.period
        };

        axios.post('http://localhost:8090/alerts', user);
    }

    render() {
        return (
            <Form onSubmit={this.handleSubmit}>
                <Form.Group>
                    <Form.Label>Name</Form.Label>
                    <Form.Control value={this.state.alertName} placeholder="Enter name" onChange={this.nameHandler}/>
                    <Form.Label>URL</Form.Label>
                    <Form.Control value={this.state.url} placeholder="Enter URL" onChange={this.urlHandler}/>
                    <Form.Label>HTTP METHOD</Form.Label>
                    <Form.Control value={this.state.httpMethod} as="select" onChange={this.httpHandler}>
                        <option>GET</option>
                        <option>POST</option>
                    </Form.Control>
                    <Form.Label>Control Period</Form.Label>
                    <Form.Control value={this.state.period} placeholder="Enter period in seconds.."
                                  onChange={this.periodHandler}/>

                </Form.Group>

                <Button variant="primary" type="submit">
                    Submit
                </Button>
            </Form>
        );
    }
}

export default AddButton;