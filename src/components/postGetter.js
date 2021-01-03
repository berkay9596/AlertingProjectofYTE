import React, {Component} from "react";
import Navbar from 'react-bootstrap/Navbar';
import AddButton from './addButton';
import axios from 'axios';
import {Table} from "react-bootstrap";
import {Chart} from "react-google-charts";


class PostGetter extends Component {
    state = {
        clicked: false,
        showClicked: false,
        graphClicked: false,
        graphAlert: null,
        currentAlerts: []
    };

    addHandleClick = () => {
        console.log("add button pressed");
        this.setState({clicked: true});
        this.setState({showClicked: false});

    };

    graphHandleClick = (alert) => {
        if (this.state.graphAlert !== null && alert.id === this.state.graphAlert.id) {
            this.setState({
                graphClicked: !this.state.graphClicked,
            });
        } else {
            this.setState({
                graphClicked: true,
                graphAlert: alert
            });
        }
    }

    showAddForm() {
        if (this.state.clicked) {
            return (
                <AddButton />
            );
        }
    }

    componentDidMount() {
        this.refreshAlertList();
    }

    refreshAlertList = () => {
        axios.get("http://localhost:8090/alerts")
            .then(resp => {
                this.setState({currentAlerts: resp.data})
            })
    }

    showUrlForm() {
        return (
            <Table striped bordered>
                <thead>
                <td>ID</td>
                <td>Name</td>
                <td>URL</td>
                <td>Method</td>
                <td>Period</td>
                <td>Graph</td>
                </thead>
                {this.state.currentAlerts.map((elem, index) => {
                    return (
                        <tr key={index}>
                            <td>{elem.id}</td>
                            <td>{elem.alertName}</td>
                            <td>{elem.url}</td>
                            <td>{elem.httpMethod}</td>
                            <td>{elem.period}</td>
                            <td><button onClick={this.graphHandleClick.bind(this, elem)}>Results</button></td>
                        </tr>
                    );
                })}
            </Table>
        );
    }

    showGraph() {
        if (this.state.graphClicked) {

            return (
                <div style={{display: "block", min_width: "50rem"}}>
                    <Chart
                        chartType="ScatterChart"
                        loader={<div>Loading Chart</div>}
                        data={
                            [[{type: "datetime", id: "timestamp", label: "Request Time"},
                                {type: "number", id: "success", label: "success"}]
                            ].concat(this.state.graphAlert.alertStatuses.map((elem) => {
                                return [new Date(elem.alertDate), elem.status]
                            }))
                        }
                        options = {{
                            vAxis: {
                                minValue: 0,
                                maxValue: 1
                            }
                        }}
                    />
                </div>
            )
        }
    }

    render() {
        return (
            <div>
                <Navbar bg="success" expand="lg">
                    <Navbar.Brand href="#">Alert System</Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                    <Navbar.Collapse id="basic-navbar-nav">
                        <button className="btn btn-primary btn-sm m-2" onClick={this.addHandleClick}>Add</button>
                        <button className="btn btn-primary btn-sm m-2" onClick={this.refreshAlertList}>Show Current
                            URLs
                        </button>
                    </Navbar.Collapse>
                </Navbar>
                <div>
                    {this.showAddForm()}
                </div>
                <div>
                    {this.showUrlForm()}
                </div>
                <div>
                    {this.showGraph()}
                </div>
            </div>
        );
    }
}

export default PostGetter;